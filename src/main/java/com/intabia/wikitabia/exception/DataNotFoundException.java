package com.intabia.wikitabia.exception;

/**
 * исключение времени выполнения,
 * характеризует невозможность найти данные в базе данных.
 */
public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String message) {
    super(message);
  }
}
