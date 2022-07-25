package com.intabia.wikitabia.exception.response;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Тело ответа при DataBotFoundException.
 */
@Getter
@Setter
@NoArgsConstructor
public class DataNotFoundResponse extends ExceptionResponse {
  /**
   * id, по которому выполнялся поиск.
   */
  private UUID id;
  /**
   * ненайденная сущность.
   */
  private String entity;

  public DataNotFoundResponse(String message, String friendlyMessage, UUID id, String entity) {
    super(message, friendlyMessage);
    this.id = id;
    this.entity = entity;
  }
}
