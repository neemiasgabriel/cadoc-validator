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
  public void visitDoc3040(final Doc3040 doc3040) {
    doc3040Validator.accept(doc3040);
  }

  @Override
  public void visitCli(final Cli cli) {
    cliValidator.accept(cli);
  }

  @Override
  public void visitOp(final Op op) {
    opValidator.accept(op);
  }

  @Override
  public void visitVenc(final Venc venc) {
    vencValidator.accept(venc);
  }

  @Override
  public void visitGar(final Gar gar) {
    garValidator.accept(gar);
  }
}