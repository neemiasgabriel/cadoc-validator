package com.picpay.cadocvalidator.core.parser;

public sealed interface Parser permits ParserImpl {
  void processFile();
}