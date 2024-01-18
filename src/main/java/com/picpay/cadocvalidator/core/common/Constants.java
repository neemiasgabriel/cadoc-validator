package com.picpay.cadocvalidator.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
  public static final Float MINIMUM_WAGE = 1302.00f;
  public static final String CNPJ_REGEX = "^\\d{8}$";
  public static final String CPF_REGEX = "^\\d{11}$";
  public static final String TEL_REGEX = "^\\d{10}$";
  public static final String MONEY_REGEX = "^[0-9]+\\.[0-9]{2}$";

  public static final String DOC_3040 = "Doc3040";
  public static final String CLI = "Cli";
  public static final String OP = "Op";
  public static final String VENC = "Venc";
  public static final String GAR = "Gar";
}