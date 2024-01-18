package com.picpay.cadocvalidator.core.log;

import com.picpay.cadocvalidator.core.enums.LogType;
import com.picpay.cadocvalidator.core.enums.TagType;

public interface ValidatorLog {
  default String log(final LogType logType, final TagType tag, final String msg) {
    return logType.type() + "Tag: " + tag.type() + " | Message: " + msg;
  }
}