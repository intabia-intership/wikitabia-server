package com.intabia.wikitabia.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело ответа при InvalidBodyException.
 */
@Getter
@Setter
@NoArgsConstructor
public class BadRequestResponse extends ExceptionResponse {
  /**
   * Название источника ошибки.
   */
  private String errorSourceName;

  /**
   * Неправильное значение, вызвавшее ошибку.
   */
  private Object wrongValue;

  /**
   * Правило для задания значения.
   */
  private String rule;
}
