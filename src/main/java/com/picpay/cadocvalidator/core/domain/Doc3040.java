package com.picpay.cadocvalidator.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

import static com.picpay.cadocvalidator.core.enums.TokenType3040.CNPJ;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.DT_BASE;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.EMAIL_RESP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.NOME_RESP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.PARTE;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.REMESSA;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.TEL_RESP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.TOTAL_CLI;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.TP_ARQ;

@Getter
@Setter
@NoArgsConstructor
public final class Doc3040 extends Tag {
  private String dtBase;
  private String cnpj;
  private String remessa;
  private String parte;
  private String tpArq;
  private String nomeResp;
  private String emailResp;
  private String telResp;
  private String totalCli;
  private List<Cli> clis = new ArrayList<>();

  public Doc3040(final XMLEvent event) {
    this.event = event;

    this.dtBase = getAttribute(DT_BASE);
    this.cnpj = getAttribute(CNPJ);
    this.remessa = getAttribute(REMESSA);
    this.parte = getAttribute(PARTE);
    this.tpArq = getAttribute(TP_ARQ);
    this.nomeResp = getAttribute(NOME_RESP);
    this.emailResp = getAttribute(EMAIL_RESP);
    this.telResp = getAttribute(TEL_RESP);
    this.totalCli = getAttribute(TOTAL_CLI);
  }
}
