package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.ValidatorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.picpay.cadocvalidator.core.common.Constants.CNPJ_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.TEL_REGEX;
import static com.picpay.cadocvalidator.core.enums.LogType.ERROR;
import static com.picpay.cadocvalidator.core.enums.LogType.INFO;
import static com.picpay.cadocvalidator.core.enums.TagType.DOC_3040;

@Slf4j
@Component
@RequiredArgsConstructor
public final class Doc3040Validator implements Validator<Doc3040>, ValidatorLog {
  private final DateValidator dateValidator;

  @Override
  public void accept(final Doc3040 doc3040) {
    validateDtBase(doc3040.getDtBase());
    validateCnpj(doc3040.getCnpj());
    validateRemessa(doc3040.getRemessa());
    validateParte(doc3040.getParte());
    validateTpArq(doc3040.getTpArq());
    validateNomeResp(doc3040.getNomeResp());
    validateEmailResp(doc3040.getEmailResp());
    validateTotalCli(doc3040.getTotalCli());
//    doc3040.setTelResp(validateTelResp());

    log.info(log(INFO, DOC_3040, "Os atributos do Doc3040 foram validados"));
  }

  private void validateDtBase(final String dtBase) {
    if (!dateValidator.isYearMonth(dtBase)) {
      log.error(log(ERROR, DOC_3040, "O DtBase da tag Doc3040 é inválido"));
    }
  }

  private void validateCnpj(final String cnpj) {
    if (cnpj == null) {
      log.error(log(ERROR, DOC_3040, "O CNPJ da tag Doc3040 não pode ser nulo"));
    }


    if (!Pattern.matches(CNPJ_REGEX, cnpj)) {
      log.error(log(ERROR, DOC_3040, "O CNPJ da tag Doc3040 é inválido"));
    }
  }

  private void validateRemessa(final String remessa) {
    if (remessa == null) {
      log.error(log(ERROR, DOC_3040, "O atributo Remessa, da tag Doc3040, não pode ser nulo"));
    }
  }

  private void validateParte(final String parte) {
    if (parte == null) {
      log.error(log(ERROR, DOC_3040, "O atributo Parte, da tag Doc3040, não pode ser nulo"));
      return;
    }

    if (!parte.equals("1")) {
      log.error(log(ERROR, DOC_3040, "O atributo Parte, da tag Doc3040, deve ser 1"));
    }
  }

  // Arquivo é enviado de forma fracionada
  private void validateTpArq(final String tpArq) {
    if (tpArq == null) {
      log.error(log(ERROR, DOC_3040, "O atributo TpArq, da tag Doc3040, não pode ser nulo"));
      return;
    }

    if (!tpArq.equals("F")) {
      log.error(log(ERROR, DOC_3040, "O atributo TpArq, da tag Doc3040, deve ser enviado de forma fracionada. Atribua o valor F ao atributo"));
    }
  }

  private void validateNomeResp(final String nomeResp) {
    if (nomeResp == null) {
      log.error(log(ERROR, DOC_3040, "O atributo NomeResp, da tag Doc3040, não pode ser nulo"));
    }
  }

  private void validateEmailResp(final String emailResp) {
    if (emailResp == null) {
      log.error(log(ERROR, DOC_3040, "O atributo EmailResp, da tag Doc3040, não pode ser nulo"));
    }
  }

  private void validateTelResp(final String telResp) {
    if (telResp == null) {
      log.error(log(ERROR, DOC_3040, "O atributo TelResp, da tag Doc3040, não pode ser nulo"));
    }

    if (!Pattern.matches(TEL_REGEX, telResp)) {
      log.error(log(ERROR, DOC_3040, "O atributo TelResp não segue as diretivas do Bacen"));
    }
  }

  private void validateTotalCli(final String totalCli) {
    if (totalCli == null) {
      log.error(log(ERROR, DOC_3040, "O atributo TotalCli, da tag Doc3040, não pode ser nulo"));
      return;
    }

    try {
      Long.parseLong(totalCli);
    } catch (NumberFormatException e) {
      log.error(log(ERROR, DOC_3040, "O atributo TotalCli, da tag Doc3040, não é um número"));
    }
  }
}