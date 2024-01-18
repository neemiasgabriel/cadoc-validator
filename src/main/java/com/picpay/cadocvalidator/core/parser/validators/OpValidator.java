package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Op;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.ValidatorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.picpay.cadocvalidator.core.enums.LogType.INFO;
import static com.picpay.cadocvalidator.core.enums.TagType.OP;

@Slf4j
@Component
public final class OpValidator implements Validator<Op>, ValidatorLog {
  @Override
  public void accept(final Op op) {
    if (op.getCli() == null) {
      throw new ParserException("A Tag Cli não pode estar nula. O empilhamento está incorreto");
    }

    validateIpoc(op.getIpoc());
    validateContrt(op.getContrt());
    validateNatuOp(op.getNatuOp());
    validateMod(op.getMod());
    validateCosif(op.getCosif());
    validateOrigemRec(op.getOrigemRec());
    validateIndx(op.getIndx());
    validatePercIndx(op.getPercIndx());
    validateVarCamb(op.getVarCamb());
    validateDtVencOp(op.getDtVencOp());
    validateClassOp(op.getClassOp());
    validateCep(op.getCep());
    validateTaxEft(op.getTaxEft());
    validateDtContr(op.getDtContr());
    validateProvConstTd(op.getProvConstTd());
    validateVlrContr(op.getVlrContr());
    validateDtaProxParcela(op.getDtaProxParcela());
    validateVlrProxParcela(op.getVlrProxParcela());
    validateQtdParcelas(op.getQtdParcelas());

    log.info(log(INFO, OP, "Os atributos do Op foram validados"));
  }

  private void validateIpoc(final String ipoc) {

  }
  private void validateContrt(final String contrt) {

  }
  private void validateNatuOp(final String natuOp) {

  }
  private void validateMod(final String mod) {

  }
  private void validateCosif(final String cosif) {

  }
  private void validateOrigemRec(final String origemRec) {

  }
  private void validateIndx(final String indx) {

  }
  private void validatePercIndx(final String percIndx) {

  }
  private void validateVarCamb(final String varCamb) {

  }
  private void validateDtVencOp(final String dtVencOp) {

  }
  private void validateClassOp(final String classOp) {

  }
  private void validateCep(final String cep) {

  }
  private void validateTaxEft(final String taxEft) {

  }
  private void validateDtContr(final String dtContr) {

  }
  private void validateProvConstTd(final String provConstTd) {

  }
  private void validateVlrContr(final String vlrContr) {

  }
  private void validateDtaProxParcela(final String dtaProxParcela) {

  }
  private void validateVlrProxParcela(final String vlrProxParcela) {

  }
  private void validateQtdParcelas(final String qtdParcelas) {

  }
}