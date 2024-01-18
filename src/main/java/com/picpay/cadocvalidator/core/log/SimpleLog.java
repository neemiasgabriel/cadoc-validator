package com.picpay.cadocvalidator.core.log;

import com.picpay.cadocvalidator.core.enums.LogType;

public interface SimpleLog {
  default String log(final LogType logType, final String msg) {
    return logType.type() + "Message: " + msg;
  }
}