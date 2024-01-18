package com.picpay.cadocvalidator.core.parser.validators;

import com.picpay.cadocvalidator.core.domain.Venc;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.ValidatorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.picpay.cadocvalidator.core.enums.LogType.INFO;
import static com.picpay.cadocvalidator.core.enums.TagType.VENC;

@Slf4j
@Component
public final class VencValidator implements Validator<Venc>, ValidatorLog {
  @Override
  public void accept(final Venc venc) {
    if (venc.getOp() == null) {
      throw new ParserException("A Tag Op não pode estar nula. O empilhamento está incorreto");
    }

    validateV110(venc.getV110());
    validateV120(venc.getV120());
    validateV130(venc.getV130());
    validateV140(venc.getV140());
    validateV150(venc.getV140());
    validateV160(venc.getV160());
    validateV165(venc.getV165());
    validateV170(venc.getV170());
    validateV175(venc.getV175());
    validateV180(venc.getV180());

    log.info(log(INFO, VENC, "Os atributos do Venc foram validados"));
  }

  private void validateV20(final String v20) {

  }

  private void validateV40(final String v40) {

  }

  private void validateV60(final String v60) {

  }

  private void validateV80(final String v80) {

  }

  private void validateV110(final String v110) {

  }

  private void validateV120(final String v120) {

  }

  private void validateV130(final String v130) {

  }

  private void validateV140(final String v140) {

  }

  private void validateV150(final String v150) {

  }

  private void validateV160(final String v160) {

  }

  private void validateV165(final String v165) {

  }

  private void validateV170(final String v170) {

  }

  private void validateV175(final String v175) {

  }

  private void validateV180(final String v180) {

  }
}