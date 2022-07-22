package com.intabia.wikitabia.exception;

import lombok.Getter;

/**
 * Базовый класс для исключений, возникших из-за ошибки в запросе на стороне клиента.
 */
@Getter
public abstract class BadRequestException extends FriendlyRuntimeException {
  /**
   * Название источника ошибки.
   */
  private final String errorSourceName;

  /**
   * Неправильное значение, вызвавшее ошибку.
   */
  private final Object wrongValue;

  /**
   * Правило для задания значения.
   */
  private final String rule;

  /**
   * Конструктор.
   *
   * @param errorSourceName     название источника ошибки
   * @param wrongValue          неправильное значение, вызвавшее ошибку
   * @param rule                правило для задания значения
   * @param message             подробное сообщение об ошибке
   * @param userFriendlyMessage понятное для пользователя сообщение
   */
  public BadRequestException(String errorSourceName, Object wrongValue, String rule, String message,
                             String userFriendlyMessage) {
    super(message, userFriendlyMessage);
    this.errorSourceName = errorSourceName;
    this.wrongValue = wrongValue;
    this.rule = rule;
  }
}
