package com.intabia.wikitabia.exception.response;

import com.intabia.wikitabia.exception.InvalidBodyException;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * тело ответа при InvalidBodyException.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvalidBodyResponse extends ExceptionResponse {
  /**
   * последовательность названий полей вплоть до ошибочного поля.
   */
  private List<String> propertyChain;

  /**
   * неправильное значение, вызвавшее ошибку.
   */
  private Object actualValue;

  /**
   * правило для задания значения.
   */
  private String rule;

  /**
   * конструктор.
   *
   * @param message         - полное сообщение
   * @param friendlyMessage - простое сообщение
   * @param propertyChain   - последовательность названий полей вплоть до ошибочного поля
   * @param actualValue     - неправильное значение, вызвавшее ошибку
   * @param rule            - правило для задания значения
   */
  public InvalidBodyResponse(String message, String friendlyMessage, List<String> propertyChain,
                             Object actualValue, String rule) {
    super(message, friendlyMessage);
    this.propertyChain = propertyChain;
    this.actualValue = actualValue;
    this.rule = rule;
  }

  /**
   * конструктор на основе исключения.
   *
   * @param ex - исключение, на которое нужно сформировать ответ
   */
  public InvalidBodyResponse(InvalidBodyException ex) {
    super(ex.getMessage(), ex.getFriendlyMessage());
    this.propertyChain = ex.getPropertyChain();
    this.actualValue = ex.getActualValue();
    this.rule = ex.getRule();
  }
}
