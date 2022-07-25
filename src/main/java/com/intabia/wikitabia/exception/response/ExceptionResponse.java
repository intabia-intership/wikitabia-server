package com.intabia.wikitabia.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * базовый класс тела ответа при исключении.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
  /**
   * полное сообщение.
   */
  private String message;
  /**
   * простое сообщение.
   */
  private String friendlyMessage;
}
