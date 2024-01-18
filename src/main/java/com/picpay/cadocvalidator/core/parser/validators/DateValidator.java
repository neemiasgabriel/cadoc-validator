package com.picpay.cadocvalidator.core.parser.validators;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public final class DateValidator {
  public boolean isYearMonth(final String dateStr) {
    final var sdf = new SimpleDateFormat("yyyy-dd");
    sdf.setLenient(false);

    return parseResult(sdf, dateStr);
  }

  public boolean isYearMonthDay(final String dateStr) {
    final var sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);

    return parseResult(sdf, dateStr);
  }

  private static boolean parseResult(final SimpleDateFormat sdf, final String dateStr) {
    try {
      sdf.parse(dateStr);
    } catch (ParseException e) {
      return false;
    }

    return true;
  }
}