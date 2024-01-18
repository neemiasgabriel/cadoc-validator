package com.picpay.cadocvalidator.core.enums;

public enum TokenType3040 {
  // XML TAG
  XML("xml"),
  VERSION("version"),
  ENCODING("encoding"),

  // DOC_3040 TAG
  DOC_3040("Doc3040"),
  DT_BASE("DtBase"),
  CNPJ("CNPJ"),
  REMESSA("Remessa"),
  PARTE("Parte"),
  TP_ARQ("TpArq"),
  NOME_RESP("NomeResp"),
  EMAIL_RESP("EmailResp"),
  TEL_RESP("TelResp"),
  TOTAL_CLI("TotalCli"),

  // CLI TAG
  CLI("Cli"),
  TP("Tp"),
  CD("Cd"),
  AUTORZC("Autorzc"),
  PORTE_CLI("PorteCli"),
  INI_RELACT_CLI("IniRelactCli"),
  FAT_ANUAL("FatAnual"),
  CLASS_CLI("ClassCli"),

  // OP TAG
  OP("Op"),
  IPOC("IPOC"),
  CONTRT("Contrt"),
  NATU_OP("NatuOp"),
  MOD("Mod"),
  COSIF("Cosif"),
  ORIGEM_REC("OrigemRec"),
  INDX("Indx"),
  PERC_INDX("PercIndx"),
  VAR_CAMB("VarCamb"),
  DT_VENC_OP("DtVencOp"),
  CLASS_OP("ClassOp"),
  CEP("CEP"),
  TAX_EFT("TaxEft"),
  DT_CONTR("DtContr"),
  PROV_CONSTTD("ProvConsttd"),
  VLR_CONTR("VlrContr"),
  DTA_PROX_PARCELA("DtaProxParcela"),
  VLR_PROX_PARCELA("VlrProxParcela"),
  QTD_PARCELAS("QtdParcelas"),

  // VENC TAG
  VENC("Venc"),
  V20("v20"),
  V40("v40"),
  V60("v60"),
  V80("v80"),
  V110("v110"),
  V120("v120"),
  V130("v130"),
  V140("v140"),
  V150("v150"),
  V160("v160"),
  V165("v165"),
  V170("v170"),
  V175("v175"),
  V180("v180"),

  // GAR TAG
  GAR("Gar"),
  VLR_ORIG("VlrOrig");

  private final String key;

  TokenType3040(final String key) {
    this.key = key;
  }

  public String key() {
    return this.key;
  }
}