package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.picpay.cadocvalidator.core.common.Constants.CNPJ_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.CPF_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.MINIMUM_WAGE;
import static com.picpay.cadocvalidator.core.common.Constants.MONEY_REGEX;

@Component
@RequiredArgsConstructor
public final class CliValidator implements Validator<Cli> {
  private final DateValidator dateValidator;

  @Override
  public void accept(final Cli cli) {
    final var tp = validateTp(cli.getTp());
    final var porteCli = validatePorteCli(tp, cli.getPorteCli());

    validateFatAnual(tp, porteCli, cli.getFatAnual());

    validateCd(cli.getCd(), tp);
    validateAutorzc(cli.getAutorzc());
    validateIniRelactCli(cli.getIniRelactCli());
    validateClassCli(cli.getClassCli());
  }

  // TP = Tipo Pessoa
  private Integer validateTp(final String tp) {
    if (tp == null) {
      throw new ParserException("O Tp, da tag Cli, não pode ser nulo");
    }

    try {
      final var intValue = Integer.parseInt(tp);

      if (!(intValue > 0 && intValue < 7)) {
        throw new ParserException("O código do Tp, da tag Cli, deve ser estar no intervalo de 1 à 6");
      }

      return intValue;
    } catch (NumberFormatException e) {
      throw new ParserException("O código do Tp, da tag Cli, não é um número");
    }
  }

  // CD = Código do Cliente (CPF/CNPJ)
  private void validateCd(final String cd, final Integer tp) {
    final var result = switch (tp) {
      case 1 -> Pattern.matches(CPF_REGEX, cd);
      case 2 -> Pattern.matches(CNPJ_REGEX, cd);
      default -> cd.length() == 14;
    };

    if (!result) {
      throw new ParserException("O atributo Cd, da tag Cli, é inválido");
    }
  }

  private void validateAutorzc(final String autorzc) {
    if (autorzc == null) {
      throw new ParserException("O atributo autorzc, da tag Cli, não pode ser nulo");
    }

    if (!(autorzc.equals("S") || autorzc.equals("N"))) {
      throw new ParserException("O atributo Autorzc, da tag Cli, é inválido");
    }
  }

  private Integer validatePorteCli(final Integer tp, final String porteCli) {
    if (porteCli == null) {
      throw new ParserException("O atributo PorteCli, da tag Cli, não pode ser nulo");
    }

    try {
      final var intValue = Integer.parseInt(porteCli);

      final var result = switch (tp) {
        case 1 -> intValue >= 0 && intValue <= 8;
        case 2 -> intValue >= 0 && intValue <= 4;
        case 3, 4, 5, 6 -> true;
        default -> false;
      };

      if (result) {
        return intValue;
      }

      throw new ParserException("O PorteCli informado, na tag Cli, é inválido");
    } catch (NumberFormatException e) {
      throw new ParserException("O código do PorteCli, da tag Cli, não é um número");
    }
  }

  private void validateIniRelactCli(final String iniRelactCli) {
    if (iniRelactCli == null) {
      throw new ParserException("O atributo InitRelactCli, da tag Cli, não pode ser nulo");
    }

    if (!dateValidator.isYearMonthDay(iniRelactCli)) {
      throw new ParserException("O atributo InitRelactCli, da tag Cli, está com a data inválida");
    }
  }

  private void validateFatAnual(final Integer tp, final Integer porteCli, final String fatAnual) {
    if (fatAnual == null) {
      throw new ParserException("O atributo FatAnual, da tag Cli, não pode ser nulo");
    }

    if (!fatAnual.isEmpty() && !Pattern.matches(MONEY_REGEX, fatAnual)) {
      throw new ParserException("O atributo FatAnual, da tag Cli, é inválido");
    }

    try {
      final var income = Float.parseFloat(fatAnual);
      final var result = switch (tp) {
        case 1 -> validateFatAnualPJ(porteCli, income);
        case 2 -> validateFatAnualPF(porteCli, income);
        default -> false;
      };

      if (!result) {
        throw new ParserException("O FatAnual, da tag Cli, não é equivalente ao porte indicado");
      }
    } catch (NumberFormatException e) {
      throw new ParserException("O código do FatAnual, da tag Cli, não é um número");
    }
  }

  private void validateClassCli(final String classCli) {
    if (classCli == null) {
      throw new ParserException("O atributo ClassCli, da tag Cli, não pode ser nulo");
    }

    if (!validateClassification(classCli)) {
      throw new ParserException("O atributo ClassCli, da tag Cli, não está dentro das classificações possíveis");
    }
  }

  private boolean validateFatAnualPJ(final Integer porteCli, final Float anualIncome) {
    final var code1 = 360_000.00;
    final var code2 = 4_800_000.00;
    final var code3 = 300_000_000.00;

    return switch (porteCli) {
      case 0 -> anualIncome <= 1.0;
      case 1 -> anualIncome <= code1;
      case 2 -> anualIncome > code1 && anualIncome <= code2;
      case 3 -> anualIncome > code2 && anualIncome <=code3;
      case 4 -> anualIncome > code3;
      default -> false;
    };
  }

  private boolean validateFatAnualPF(final Integer porteCli, final Float income) {
    final var code3 = MINIMUM_WAGE * 2;
    final var code4 = MINIMUM_WAGE * 3;
    final var code5 = MINIMUM_WAGE * 5;
    final var code6 = MINIMUM_WAGE * 10;
    final var code7 = MINIMUM_WAGE * 20;

    return switch (porteCli) {
      case 0 -> income <= 1.0;
      case 1 -> income == null;
      case 2 -> income > 1.0 && income <= MINIMUM_WAGE;
      case 3 -> income > MINIMUM_WAGE && income <= code3;
      case 4 -> income > code3 && income <= code4;
      case 5 -> income > code4 && income <= code5;
      case 6 -> income > code5 && income <= code6;
      case 7 -> income > code6 && income <= code7;
      case 8 -> income > code7;
      default -> false;
    };
  }

  private boolean validateClassification(final String classCli) {
    return switch (classCli) {
      case "AA", "A", "B", "C", "D", "E", "F", "G", "H" -> true;
      default -> false;
    };
  }
}
