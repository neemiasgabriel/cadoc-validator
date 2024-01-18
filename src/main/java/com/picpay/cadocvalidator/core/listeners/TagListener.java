package com.picpay.cadocvalidator.core.listeners;

import com.picpay.cadocvalidator.core.domain.Tag;

public interface TagListener {
  void beginTag(final Tag tag);
  void endTag(final Tag tag);
  void unexpectedTag(final Tag tag);
}