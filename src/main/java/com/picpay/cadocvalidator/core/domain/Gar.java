package com.picpay.cadocvalidator.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.stream.events.XMLEvent;

import static com.picpay.cadocvalidator.core.enums.TokenType3040.TP;
import static com.picpay.cadocvalidator.core.enums.TokenType3040.VLR_ORIG;

/**
 * Garantias
 * Tp: Tipo e Subtipo da Garantia - Bens Arrendados, Garantia Fidejussória, etc
 * Nas operações de arrendamento financeiro de veículos automotores (Tp 1001) é obrigatório que o VlrOrig seja preenchido
 */
@Getter
@Setter
@NoArgsConstructor
public final class Gar extends Tag {
  private String tp;
  private String vlrOrig;
  private Op op;

  public Gar(final XMLEvent event, final Op op) {
    this.event = event;
    this.op = op;
    this.tp = getAttribute(TP);
    this.vlrOrig = getAttribute(VLR_ORIG);
  }
}
