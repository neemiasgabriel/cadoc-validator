package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Tag;

public interface LifoStack {
  Tag peek();
  Tag pop(final String expectedTag);
  void push(Tag tag);
}