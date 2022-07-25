package com.intabia.wikitabia.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * тело ответа при InvalidBodyException.
 */
@Getter
@Setter
@NoArgsConstructor
public class InvalidBodyResponse extends ExceptionResponse {
  /**
   * последовательность названий полей вплоть до ошибочного поля.
   */
  private String[] propertyChain;

  /**
   * неправильное значение, вызвавшее ошибку.
   */
  private Object actualValue;

  /**
   * правило для задания значения.
   */
  private String rule;

  public InvalidBodyResponse(String message, String friendlyMessage, String[] propertyChain, Object actualValue, String rule) {
    super(message, friendlyMessage);
    this.propertyChain = propertyChain;
    this.actualValue = actualValue;
    this.rule = rule;
  }
}
