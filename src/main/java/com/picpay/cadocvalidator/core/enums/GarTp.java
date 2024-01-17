package com.picpay.cadocvalidator.core.enums;

public enum GarTp {
  SEGUROS_OUTROS("0889"),
  PESSOA_FISICA("0901"),
  PESSOA_JURIDICA("0902"),
  PESSOA_FISICA_EXTERIOR("0903"),
  PESSOA_JURIDICA_EXTERIOR("0904"),

  BEM_ARRENDADO_VEICULO("1001"),
  BEM_ARRENDADO_OUTROS("1002");

  private final String code;

  GarTp(String code) {
    this.code = code;
  }

  public String code() {
    return this.code;
  }
}
