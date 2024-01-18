package com.picpay.cadocvalidator.core.parser;

import com.picpay.cadocvalidator.core.domain.Tag;
import com.picpay.cadocvalidator.core.exceptions.ParserException;
import com.picpay.cadocvalidator.core.log.SimpleLog;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.picpay.cadocvalidator.core.enums.LogType.ERROR;
import static com.picpay.cadocvalidator.core.enums.LogType.INFO;

@Slf4j
@Component
public final class TagStack implements LifoStack, SimpleLog {
  private final Deque<Tag> deque = new ArrayDeque<>();

  @Override
  public Tag peek() {
    return deque.peek();
  }

  @Override
  public Tag pop(final String expectedTag) {
    final var poped = deque.pop();

    if (!poped.tagName().equals(expectedTag)) {
      final var message = unexpectedTag(expectedTag);

      log.error(log(ERROR, message));
      throw new ParserException(message);
    } else {
      endTagMessage(poped.tagName());
    }

    return poped;
  }

  @Override
  public void push(final Tag tag) {
    beginTagMessage(tag.tagName());
    deque.push(tag);
  }

  private String unexpectedTag(final String tagName) {
    return "Tag " + tagName + " não esperada.";
  }

  private void beginTagMessage(final String tagName) {
    log.info(log(INFO, "Tag Aberta: " + tagName));
  }

  private void endTagMessage(final String tagName) {
    log.info(log(INFO, "Tag Fechada: " + tagName));
  }
}