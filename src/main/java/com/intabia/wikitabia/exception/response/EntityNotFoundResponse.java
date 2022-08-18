package com.intabia.wikitabia.exception.response;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело ответа при EntityNotFoundException.
 */
@Getter
@Setter
@NoArgsConstructor
public class EntityNotFoundResponse extends ExceptionResponse {
  /**
   * Id сущности, по которому выполнялся поиск.
   */
  private UUID entityId;
  /**
   * Имя класса ненайденной сущности.
   */
  private String entityClassName;
}
