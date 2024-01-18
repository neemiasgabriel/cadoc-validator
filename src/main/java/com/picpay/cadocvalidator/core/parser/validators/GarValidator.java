package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Gar;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.ValidatorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.picpay.cadocvalidator.core.enums.LogType.ERROR;
import static com.picpay.cadocvalidator.core.enums.LogType.INFO;
import static com.picpay.cadocvalidator.core.enums.TagType.GAR;

@Slf4j
@Component
public final class GarValidator implements Validator<Gar>, ValidatorLog {
  @Override
  public void accept(final Gar gar) {
    if (gar.getOp() == null) {
      log.error(log(ERROR, GAR, "A Tag Op não pode estar nula. O empilhamento está errado"));
    }

    validateTp(gar.getTp());
    validateVlrOrig(gar.getVlrOrig());

    log.info(log(INFO, GAR, "Os atributos do Gar foram validados"));
  }

  private String validateTp(final String tp) {
    if (tp == null) {
      log.error(log(ERROR, GAR, "O atributo Tp, da tag Gar, não pode estar vazio"));
      return null;
    }

    return switch (tp) {
      case "0889", "0101", "0102", "0103", "0104", "0105", "0106", "0107", "0108", "0199" -> tp;
      default -> {
        log.error(log(ERROR, GAR, "O atributo Tp, da tag Gar, é inválido para o arquivo"));
        yield null;
      }
    };
  }

  private void validateVlrOrig(final String vlrOrig) {
    if (vlrOrig == null) {
      log.error(log(ERROR, GAR, "Atributo VlrOrig, da tag Gar, não pode ser vazio"));
    }
  }
}