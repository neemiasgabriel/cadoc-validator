package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.domain.Tag;
import com.picpay.cadocvalidator.core.domain.Venc;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.SimpleLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.picpay.cadocvalidator.core.common.Constants.CLI;
import static com.picpay.cadocvalidator.core.common.Constants.DOC_3040;
import static com.picpay.cadocvalidator.core.common.Constants.GAR;
import static com.picpay.cadocvalidator.core.common.Constants.OP;
import static com.picpay.cadocvalidator.core.common.Constants.VENC;
import static com.picpay.cadocvalidator.core.enums.LogType.ERROR;
import static com.picpay.cadocvalidator.core.enums.LogType.INFO;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ParserImpl implements Parser, SimpleLog {
  private final TagVisitor tagVisitor;
  private final TagStack stack;

  @Override
  @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
  public void processFile() {
    final var resource = new ClassPathResource("Exemplo3040.xml");
    final var xmlInputFactory = XMLInputFactory.newInstance();

    try {
      final var reader = xmlInputFactory.createXMLEventReader(new FileInputStream(resource.getFile()));

      if (!reader.hasNext()) return;

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
              doc3040 = new Doc3040(nextEvent);
              doc3040.accept(tagVisitor);
              stack.push(doc3040);
              break;
            case CLI:
              cli = new Cli(nextEvent, doc3040);
              cli.accept(tagVisitor);
              stack.push(cli);
              break;
            case OP:
              op = new Op(nextEvent, cli);
              op.accept(tagVisitor);
              stack.push(op);
              break;
            case VENC:
              final var venc = new Venc(nextEvent, op);
              venc.accept(tagVisitor);
              stack.push(venc);
              break;
            case GAR:
              final var gar = new Gar(nextEvent, op);
              gar.accept(tagVisitor);
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
              stack.pop(DOC_3040);
              doc3040 = null;
              break;
            case CLI:
              stack.pop(CLI);
              cli = null;
              break;
            case OP:
              stack.pop(OP);
              op = null;
              break;
            case VENC:
              if (stack.pop(VENC) instanceof Venc venc) {
                final var result = checkOp(stack.peek(), venc);

                if (!result) {
                  unexpectedTag(tagName);
                }
              }

              break;
            case GAR:
              if (stack.pop(GAR) instanceof Gar gar) {
                final var result = checkOp(stack.peek(), gar);

                if (!result) {
                  unexpectedTag(tagName);
                }
              }

              break;
            default:
              log.info("Tag Pulada: " + tagName);
          }
        }
      }

      log.info(INFO.type() + "Fim do processamento");
    } catch (XMLStreamException | IOException e) {
      throw new ParserException(e.getMessage());
    }
  }

  private boolean checkOp(final Tag stackTop, final Tag currentTag) {
    if ((currentTag instanceof Venc || currentTag instanceof Gar) && (stackTop instanceof Op)) {
      return true;
    }

    final var message = "Tag não esperada dentro da tag Op";

    log.error(log(ERROR, message));
    throw new ParserException(message);
  }

  private void unexpectedTag(final String tagName) {
    log.error(log(ERROR, "Tag " + tagName + " não esperada."));
  }

  private void ignoredTag(final String tagName) {
    log.info(log(INFO, "Tag ignorada: " + tagName));
  }
}