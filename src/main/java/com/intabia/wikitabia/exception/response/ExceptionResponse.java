package com.intabia.wikitabia.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Базовый класс тела ответа при исключении.
 */
@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {
  /**
   * Подробное сообщение об ошибке.
   */
  private String message;
  /**
   * Понятное для пользователя сообщение.
   */
  private String userFriendlyMessage;
}
