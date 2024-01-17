package com.picpay.cadocvalidator.core.domain;

import com.google.gson.Gson;
import com.picpay.cadocvalidator.core.enums.TokenType3040;

import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;

public abstract sealed class Tag permits Doc3040, Cli, Op, Venc, Gar {
  protected XMLEvent event;

  public String toJson() {
    return new Gson().toJson(this);
  }

  public String getAttribute(final TokenType3040 attribute) {
    final var attr = event.asStartElement().getAttributeByName(new QName(attribute.key()));
    return attr != null ? attr.getValue() : null;
  }
}
