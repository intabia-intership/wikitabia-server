package com.intabia.wikitabia.exceptions;

/**
 * Кастомное исключение. Выбрасывать при невозможности найти данные в БД
 */
public class CustomException extends RuntimeException {

  public CustomException(Throwable th) {
    super(th);
  }

  public CustomException(String message) {
    super((message));
  }
}
