package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.domain.Venc;
import com.picpay.cadocvalidator.core.parser.validators.CliValidator;
import com.picpay.cadocvalidator.core.parser.validators.Doc3040Validator;
import com.picpay.cadocvalidator.core.parser.validators.GarValidator;
import com.picpay.cadocvalidator.core.parser.validators.OpValidator;
import com.picpay.cadocvalidator.core.parser.validators.VencValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.stream.events.XMLEvent;

@Component
@RequiredArgsConstructor
public final class TagVisitorImpl implements TagVisitor {
  private final Doc3040Validator doc3040Validator;
  private final CliValidator cliValidator;
  private final OpValidator opValidator;
  private final VencValidator vencValidator;
  private final GarValidator garValidator;

  @Override
  public Doc3040 visitDoc3040(final XMLEvent element) {
    final var doc3040 = new Doc3040(element);
    doc3040Validator.accept(doc3040);
    return doc3040;
  }

  @Override
  public Cli visitCli(final XMLEvent cliEvent, final Doc3040 doc3040) {
    final var cli = new Cli(cliEvent, doc3040);
    cliValidator.accept(cli);
    return cli;
  }

  @Override
  public Op visitOp(final XMLEvent opEvent, final Cli cli) {
    final var op = new Op(opEvent, cli);
    opValidator.accept(op);
    op.setCli(cli);
    return op;
  }

  @Override
  public Venc visitVenc(final XMLEvent vencEvent, final Op op) {
    final var venc = new Venc(vencEvent, op);
    vencValidator.accept(venc);
    venc.setOp(op);
    op.setVenc(venc);
    return venc;
  }

  @Override
  public Gar visitGar(final XMLEvent garEvent, final Op op) {
    final var gar = new Gar(garEvent, op);
    gar.setOp(op);
    op.setGar(gar);
    garValidator.accept(gar);
    return gar;
  }
}
