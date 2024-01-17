package com.picpay.cadocvalidator.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.XMLEvent;

import static com.picpay.cadocvalidator.core.enums.TokenType3040.V110;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V120;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V130;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V140;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V150;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V160;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V165;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V170;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V175;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V180;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V20;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V40;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V60;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.V80;

@Getter
@Setter
@NoArgsConstructor
public final class Venc extends Tag {
  private String v20;
  private String v40;
  private String v60;
  private String v80;
  private String v110;
  private String v120;
  private String v130;
  private String v140;
  private String v150;
  private String v160;
  private String v165;
  private String v170;
  private String v175;
  private String v180;
  private Op op;

  public Venc(final XMLEvent event, final Op op) {
    this.event = event;
    this.op = op;

    this.v20 = getAttribute(V20);
    this.v40 = getAttribute(V40);
    this.v60 = getAttribute(V60);
    this.v80 = getAttribute(V80);
    this.v110 = getAttribute(V110);
    this.v120 = getAttribute(V120);
    this.v130 = getAttribute(V130);
    this.v140 = getAttribute(V140);
    this.v150 = getAttribute(V150);
    this.v160 = getAttribute(V160);
    this.v165 = getAttribute(V165);
    this.v170 = getAttribute(V170);
    this.v175 = getAttribute(V175);
    this.v180 = getAttribute(V180);
  }
}
