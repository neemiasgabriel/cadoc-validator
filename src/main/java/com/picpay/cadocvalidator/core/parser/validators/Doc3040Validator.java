package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Doc3040;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.picpay.cadocvalidator.core.common.Constants.CNPJ_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.TEL_REGEX;

@Component
@RequiredArgsConstructor
public final class Doc3040Validator implements Validator<Doc3040> {
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
//    validateTelResp(doc3040.getTelResp());
    validateTotalCli(doc3040.getTotalCli());
  }

  private void validateDtBase(final String dtBase) {
    if (!dateValidator.isYearMonth(dtBase)) {
      throw new ParserException("O DtBase da tag Doc3040 é inválido");
    }
  }

  private void validateCnpj(final String cnpj) {
    if (cnpj == null) {
      throw new ParserException("O CNPJ da tag Doc3040 não pode ser nulo");
    }


    if (!Pattern.matches(CNPJ_REGEX, cnpj)) {
      throw new ParserException("O CNPJ da tag Doc3040 é inválido");
    }
  }

  private void validateRemessa(final String remessa) {
    if (remessa == null) {
      throw new ParserException("O atributo Remessa, da tag Doc3040, não pode ser nulo");
    }
  }

  private void validateParte(final String parte) {
    if (parte == null) {
      throw new ParserException("O atributo Parte, da tag Doc3040, não pode ser nulo");
    }

    if (!parte.equals("1")) {
      throw new ParserException("O atributo Parte, da tag Doc3040, deve ser 1");
    }
  }

  // Arquivo é enviado de forma fracionada
  private void validateTpArq(final String tpArq) {
    if (tpArq == null) {
      throw new ParserException("O atributo TpArq, da tag Doc3040, não pode ser nulo");
    }

    if (!tpArq.equals("F")) {
      throw new ParserException("O atributo TpArq, da tag Doc3040, deve ser enviado de forma fracionada. Atribua o valor F ao atributo");
    }
  }

  private void validateNomeResp(final String nomeResp) {
    if (nomeResp == null) {
      throw new ParserException("O atributo NomeResp, da tag Doc3040, não pode ser nulo");
    }
  }

  private void validateEmailResp(final String emailResp) {
    if (emailResp == null) {
      throw new ParserException("O atributo EmailResp, da tag Doc3040, não pode ser nulo");
    }
  }

  private void validateTelResp(final String telResp) {
    if (telResp == null) {
      throw new ParserException("O atributo TelResp, da tag Doc3040, não pode ser nulo");
    }

    if (!Pattern.matches(TEL_REGEX, telResp)) {
      throw new ParserException("O atributo TelResp não segue as diretivas do Bacen");
    }
  }

  private void validateTotalCli(final String totalCli) {
    if (totalCli == null) {
      throw new ParserException("O atributo TotalCli, da tag Doc3040, não pode ser nulo");
    }

    try {
      Long.parseLong(totalCli);
    } catch (NumberFormatException e) {
      throw new ParserException("O atributo TotalCli, da tag Doc3040, não é um número");
    }
  }
}
