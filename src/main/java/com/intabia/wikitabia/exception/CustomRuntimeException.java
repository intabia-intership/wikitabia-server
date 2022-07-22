package com.intabia.wikitabia.exception;

import lombok.Getter;

/**
 * базовый класс для собственных исключений.
 */
@Getter
public class CustomRuntimeException extends RuntimeException {
  private final String message;
  private final String friendlyMessage;

  protected CustomRuntimeException(String message, String friendlyMessage) {
    super();
    this.message = message;
    this.friendlyMessage = friendlyMessage;
  }
}
