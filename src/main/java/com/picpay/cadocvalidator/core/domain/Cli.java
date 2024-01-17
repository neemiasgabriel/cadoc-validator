package com.picpay.cadocvalidator.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

import static com.picpay.cadocvalidator.core.enums.TokenType3040.AUTORZC;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.CD;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.CLASS_CLI;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.FAT_ANUAL;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.INI_RELACT_CLI;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.PORTE_CLI;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.TP;

@Getter
@Setter
@NoArgsConstructor
public final class Cli extends Tag {
  private String tp;
  private String cd;
  private String autorzc;
  private String porteCli;
  private String iniRelactCli;
  private String fatAnual;
  private String classCli;
  private List<Op> ops = new ArrayList<>();
  private Doc3040 doc3040;

  public Cli(final XMLEvent event, final Doc3040 doc3040) {
    this.event = event;
    this.doc3040 = doc3040;

    this.tp = getAttribute(TP);
    this.cd = getAttribute(CD);
    this.autorzc = getAttribute(AUTORZC);
    this.porteCli = getAttribute(PORTE_CLI);
    this.iniRelactCli = getAttribute(INI_RELACT_CLI);
    this.fatAnual = getAttribute(FAT_ANUAL);
    this.classCli = getAttribute(CLASS_CLI);
  }
}
