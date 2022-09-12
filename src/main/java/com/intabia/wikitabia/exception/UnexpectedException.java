package com.intabia.wikitabia.exception;

/**
 * Исключение, выбрасываемое в случаях,
 * когда никаких исключений не предвиделось,
 * поскольку были совершены предварительные проверки
 * (например, ошибка в аргументе после валидации входных аргументов сервиса).
 */
public class UnexpectedException extends FriendlyRuntimeException {
  private static final String MESSAGE = "Произошла непредвиденная ошибка";

  public UnexpectedException() {
    super(MESSAGE, MESSAGE);
  }
}
