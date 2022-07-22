package com.intabia.wikitabia.exception;

/**
 * базовый класс для исключений типа bad request.
 */
public class BadRequestException extends CustomRuntimeException {
  protected BadRequestException(String message, String friendlyMessage) {
    super(message, friendlyMessage);
  }
}
