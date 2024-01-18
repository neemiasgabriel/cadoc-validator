package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Cli;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.ValidatorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.picpay.cadocvalidator.core.common.Constants.CNPJ_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.CPF_REGEX;
import static com.picpay.cadocvalidator.core.common.Constants.MINIMUM_WAGE;
import static com.picpay.cadocvalidator.core.common.Constants.MONEY_REGEX;
import static com.picpay.cadocvalidator.core.enums.LogType.ERROR;
import static com.picpay.cadocvalidator.core.enums.LogType.INFO;
import static com.picpay.cadocvalidator.core.enums.TagType.CLI;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CliValidator implements Validator<Cli>, ValidatorLog {
  private final DateValidator dateValidator;

  @Override
  public void accept(final Cli cli) {
    if (cli.getDoc3040() == null) {
      throw new ParserException("A Tag Doc3040 não pode estar nula. O empilhamento está errado");
    }

    final var tp = validateTp(cli.getTp());

    if (tp == null) {
      log.error(log(ERROR, CLI, "Não é possível validar o Porte Cli, o Faturamento Anual e o Cd pois o Tp, da tag Cli, está nulo"));
    }

    final var porteCli = validatePorteCli(tp, cli.getPorteCli());
    validateFatAnual(tp, porteCli, cli.getFatAnual());

    validateCd(cli.getCd(), tp);
    validateAutorzc(cli.getAutorzc());
    validateIniRelactCli(cli.getIniRelactCli());
    validateClassCli(cli.getClassCli());

    log.info(log(INFO, CLI, "Os atributo do Cli foram validados"));
  }

  // TP = Tipo Pessoa
  private Integer validateTp(final String tp) {
    if (tp == null) {
      log.error(log(ERROR, CLI, "O Tp, da tag Cli, não pode ser nulo"));
      return null;
    }

    try {
      final var intValue = Integer.parseInt(tp);

      if (!(intValue > 0 && intValue < 7)) {
        log.error(log(ERROR, CLI, "O código do Tp, da tag Cli, deve ser estar no intervalo de 1 à 6"));
      }

      return intValue;
    } catch (NumberFormatException e) {
      log.error(log(ERROR, CLI, "O código do Tp, da tag Cli, não é um número"));
      return null;
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
      log.error(log(ERROR, CLI, "O atributo Cd, da tag Cli, é inválido"));
    }
  }

  private void validateAutorzc(final String autorzc) {
    if (autorzc == null) {
      log.error(log(ERROR, CLI, "O atributo autorzc, da tag Cli, não pode ser nulo"));
      return;
    }

    if (!(autorzc.equals("S") || autorzc.equals("N"))) {
      log.error(log(ERROR, CLI, "O atributo Autorzc, da tag Cli, é inválido"));
    }
  }

  private Integer validatePorteCli(final Integer tp, final String porteCli) {
    if (porteCli == null) {
      log.error(log(ERROR, CLI, "O atributo PorteCli, da tag Cli, não pode ser nulo"));
      return null;
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

      log.error(log(ERROR, CLI, "O PorteCli informado, na tag Cli, é inválido"));
      return null;
    } catch (NumberFormatException e) {
      log.error(log(ERROR, CLI, "O código do PorteCli, da tag Cli, não é um número"));
      return null;
    }
  }

  private void validateIniRelactCli(final String iniRelactCli) {
    if (iniRelactCli == null) {
      log.error(log(ERROR, CLI, "O atributo InitRelactCli, da tag Cli, não pode ser nulo"));
    }

    if (!dateValidator.isYearMonthDay(iniRelactCli)) {
      log.error(log(ERROR, CLI, "O atributo InitRelactCli, da tag Cli, está com a data inválida"));
    }
  }

  private void validateFatAnual(final Integer tp, final Integer porteCli, final String fatAnual) {
    if (fatAnual == null) {
      log.error(log(ERROR, CLI, "O atributo FatAnual, da tag Cli, não pode ser nulo"));
      return;
    }

    if (!fatAnual.isEmpty() && !Pattern.matches(MONEY_REGEX, fatAnual)) {
      log.error(log(ERROR, CLI, "O atributo FatAnual, da tag Cli, é inválido"));
    }

    try {
      final var income = Float.parseFloat(fatAnual);
      final var result = switch (tp) {
        case 1 -> validateFatAnualPF(porteCli, income);
        case 2 -> validateFatAnualPJ(porteCli, income);
        default -> false;
      };

      if (!result) {
        log.error(log(ERROR, CLI, "O FatAnual, da tag Cli, não é equivalente ao porte indicado"));
      }
    } catch (NumberFormatException e) {
      log.error(log(ERROR, CLI, "O código do FatAnual, da tag Cli, não é um número"));
    }
  }

  private void validateClassCli(final String classCli) {
    if (classCli == null) {
      log.error(log(ERROR, CLI, "O atributo ClassCli, da tag Cli, não pode ser nulo"));
      return;
    }

    if (!validateClassification(classCli)) {
      log.error(log(ERROR, CLI, "O atributo ClassCli, da tag Cli, não está dentro das classificações possíveis"));
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