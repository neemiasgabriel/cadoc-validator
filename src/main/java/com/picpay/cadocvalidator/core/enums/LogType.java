package com.picpay.cadocvalidator.core.enums;

public enum LogType {
  ERROR("[ERROR] "),
  INFO("[INFO] ");

  private final String type;

  LogType(final String type) {
    this.type = type;
  }

  public String type() {
    return this.type;
  }
}