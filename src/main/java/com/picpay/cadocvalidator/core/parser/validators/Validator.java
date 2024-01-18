package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Tag;

public sealed interface Validator<T extends Tag> permits Doc3040Validator,
    CliValidator, OpValidator, VencValidator, GarValidator {
  void accept(final T event);
}