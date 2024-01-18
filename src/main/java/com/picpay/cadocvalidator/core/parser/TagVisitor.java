package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.domain.Venc;

import javax.xml.stream.events.XMLEvent;

public interface TagVisitor {
  void visitDoc3040(final Doc3040 doc3040);
  void visitCli(final Cli cli);
  void visitOp(final Op op);
  void visitVenc(final Venc gar);
  void visitGar(final Gar gar);
}