package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.domain.Venc;

import javax.xml.stream.events.XMLEvent;

public interface TagVisitor {
  Doc3040 visitDoc3040(final XMLEvent element);
  Cli visitCli(final XMLEvent cliEvent, final Doc3040 doc3040);
  Op visitOp(final XMLEvent opEvent, final Cli cli);
  Venc visitVenc(final XMLEvent vencEvent, final Op op);
  Gar visitGar(final XMLEvent garEvent, final Op op);
}
