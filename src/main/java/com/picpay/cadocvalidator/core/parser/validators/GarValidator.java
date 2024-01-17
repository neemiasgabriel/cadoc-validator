package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import org.springframework.stereotype.Component;

@Component
public final class GarValidator implements Validator<Gar> {
  @Override
  public void accept(final Gar gar) {
    validateTp(gar.getTp());
    validateVlrOrig(gar.getVlrOrig());
  }

  private String validateTp(final String tp) {
    if (tp == null) {
      throw new ParserException("O atributo Tp, da tag Gar, não pode estar vazio");
    }

    return switch (tp) {
      case "0889", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0199" -> tp;
      default -> throw new ParserException("O atributo Tp, da tag Gar, é inválido para o arquivo");
    };
  }

  private void validateVlrOrig(final String vlrOrig) {
    if (vlrOrig == null) {
      throw new ParserException("O atributo VlrOrig, da tag Gar, não pode estar vazio");
    }
  }
}
