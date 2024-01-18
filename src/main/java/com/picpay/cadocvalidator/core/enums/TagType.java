package com.picpay.cadocvalidator.core.enums;

public enum TagType {
  DOC_3040("Doc3040"),
  CLI("Cli"),
  OP("Op"),
  VENC("Venc"),
  GAR("Gar");

  private final String type;

  TagType(final String type) {
    this.type = type;
  }

  public String type() {
    return this.type;
  }
}