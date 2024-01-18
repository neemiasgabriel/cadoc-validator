package com.picpay.cadocvalidator.core.domain;

import com.picpay.cadocvalidator.core.parser.TagVisitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.XMLEvent;

import static com.picpay.cadocvalidator.core.enums.TokenType3040.CEP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.CLASS_OP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.CONTRT;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.COSIF;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.DTA_PROX_PARCELA;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.DT_CONTR;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.DT_VENC_OP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.INDX;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.IPOC;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.MOD;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.NATU_OP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.ORIGEM_REC;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.PERC_INDX;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.PROV_CONSTTD;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.QTD_PARCELAS;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.TAX_EFT;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.VAR_CAMB;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.VLR_CONTR;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.VLR_PROX_PARCELA;

@Getter
@Setter
@NoArgsConstructor
public final class Op extends Tag {
  private String ipoc;
  private String contrt;
  private String natuOp;
  private String mod;
  private String cosif;
  private String origemRec;
  private String indx;
  private String percIndx;
  private String varCamb;
  private String dtVencOp;
  private String classOp;
  private String cep;
  private String taxEft;
  private String dtContr;
  private String provConstTd;
  private String vlrContr;
  private String dtaProxParcela;
  private String vlrProxParcela;
  private String qtdParcelas;
  private Venc venc;
  private Gar gar;
  private Cli cli;

  public Op(final XMLEvent event, final Cli cli) {
    this.event = event;
    this.cli = cli;

    this.ipoc = getAttribute(IPOC);
    this.contrt = getAttribute(CONTRT);
    this.natuOp = getAttribute(NATU_OP);
    this.mod = getAttribute(MOD);
    this.cosif = getAttribute(COSIF);
    this.origemRec = getAttribute(ORIGEM_REC);
    this.indx = getAttribute(INDX);
    this.percIndx = getAttribute(PERC_INDX);
    this.varCamb = getAttribute(VAR_CAMB);
    this.dtVencOp = getAttribute(DT_VENC_OP);
    this.classOp = getAttribute(CLASS_OP);
    this.cep = getAttribute(CEP);
    this.taxEft = getAttribute(TAX_EFT);
    this.dtContr = getAttribute(DT_CONTR);
    this.provConstTd = getAttribute(PROV_CONSTTD);
    this.vlrContr = getAttribute(VLR_CONTR);
    this.dtaProxParcela = getAttribute(DTA_PROX_PARCELA);
    this.vlrProxParcela = getAttribute(VLR_PROX_PARCELA);
    this.qtdParcelas = getAttribute(QTD_PARCELAS);
  }

  @Override
  public void accept(final TagVisitor visitor) {
    visitor.visitOp(this);
  }
}