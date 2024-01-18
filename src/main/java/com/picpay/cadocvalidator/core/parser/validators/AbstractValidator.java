package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.enums.TokenType3040;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public abstract class AbstractValidator {
  protected XMLEvent event;

  public Attribute getAttribute(final TokenType3040 attribute) {
    return event.asStartElement().getAttributeByName(new QName(attribute.key()));
  }
}