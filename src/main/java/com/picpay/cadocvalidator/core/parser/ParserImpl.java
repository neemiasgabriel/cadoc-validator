package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.domain.Tag;
import com.picpay.cadocvalidator.core.domain.Venc;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public final class ParserImpl implements Parser {
  private static final String DOC_3040 = "Doc3040";
  private static final String CLI = "Cli";
  private static final String OP = "Op";
  private static final String VENC = "Venc";
  private static final String GAR = "Gar";

  private final TagVisitor tagVisitor;

  @Override
  @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
  public void processFile() {
    final var resource = new ClassPathResource("cadoc3040.xml");
    final var xmlInputFactory = XMLInputFactory.newInstance();

    try {
      final var reader = xmlInputFactory.createXMLEventReader(new FileInputStream(resource.getFile()));

      if (!reader.hasNext()) return;

      final var stack = new Stack<Tag>();

      XMLEvent nextEvent;
      Doc3040 doc3040 = null;
      Cli cli = null;
      Op op = null;

      while (reader.hasNext()) {
        nextEvent = reader.nextEvent();

        if (nextEvent.isStartElement()) {
          final var startElement = nextEvent.asStartElement();
          final var tagName = startElement.getName().getLocalPart();

          switch (tagName) {
            case DOC_3040:
              beginTagMessage(tagName);
              doc3040 = tagVisitor.visitDoc3040(nextEvent);
              stack.push(doc3040);
              break;
            case CLI:
              beginTagMessage(tagName);
              cli = tagVisitor.visitCli(nextEvent, doc3040);
              stack.push(cli);
              break;
            case OP:
              beginTagMessage(tagName);
              op = tagVisitor.visitOp(nextEvent, cli);
              stack.push(op);
              break;
            case VENC:
              beginTagMessage(tagName);
              final var venc = tagVisitor.visitVenc(nextEvent, op);
              stack.push(venc);
              break;
            case GAR:
              beginTagMessage(tagName);
              final var gar = tagVisitor.visitGar(nextEvent, op);
              stack.push(gar);
              break;
            default:
              ignoredTag(tagName);
          }
        } else if (nextEvent.isEndElement()) {
          final var endElement = nextEvent.asEndElement();
          final var tagName = endElement.getName().getLocalPart();

          // Valida se os elementos estão seguindo o padrão de empilhamento
          switch (tagName) {
            case DOC_3040:
              final var docExpr = stack.pop();

              if (docExpr instanceof Doc3040) {
                endTagMessage(tagName);
              } else {
                throw new ParserException(unexpectedTag(tagName));
              }
              break;
            case CLI:
              final var cliExp = stack.pop();

              if (cliExp instanceof Cli) {
                endTagMessage(tagName);
              } else {
                throw new ParserException(unexpectedTag(tagName));
              }
              break;
            case OP:
              final var opTag = stack.pop();

              if (opTag instanceof Op) {
                endTagMessage(tagName);
              } else {
                throw new ParserException(unexpectedTag(tagName));
              }

              break;
            case VENC:
              if (stack.pop() instanceof Venc venc) {
                final var result = checkOp(stack.peek(), venc);

                if (result) {
                  endTagMessage(tagName);
                } else {
                  throw new ParserException(unexpectedTag(tagName));
                }
              } else {
                throw new ParserException(unexpectedTag(tagName));
              }

              break;
            case GAR:
              if (stack.pop() instanceof Gar gar) {
                final var result = checkOp(stack.peek(), gar);

                if (result) {
                  endTagMessage(tagName);
                } else {
                  throw new ParserException(unexpectedTag(tagName));
                }
              } else {
                throw new ParserException(unexpectedTag(tagName));
              }

              break;
            default:
              System.out.println("Tag Pulada: " + tagName);
          }
        }
      }

      System.out.println("Fim do processamento");
    } catch (IOException | XMLStreamException e) {
      throw new RuntimeException(e);
    }
  }

  private String unexpectedTag(final String tagName) {
    return "Tag " + tagName + " não esperada";
  }

  private void beginTagMessage(final String tagName) {
    System.out.println("Tag Aberta: " + tagName);
  }

  private void endTagMessage(final String tagName) {
    System.out.println("Tag Fechada: " + tagName);
  }

  private void ignoredTag(final String tagName) {
    System.out.println("Tag ignorada: " + tagName);
  }

  private boolean checkOp(final Tag stackTop, final Tag currentTag) {
    if ((currentTag instanceof Venc || currentTag instanceof Gar) && (stackTop instanceof Op)) {
      return true;
    }

    throw new ParserException("Tag não esperada dentro da tag Op");
  }
}
