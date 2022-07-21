package com.intabia.wikitabia.exception.response;

import com.intabia.wikitabia.exception.DataNotFoundException;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Тело ответа при DataBotFoundException.
 */
@Getter
@Setter
@SuperBuilder
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

  /**
   * конструктор.
   *
   * @param message         - полное сообщение
   * @param friendlyMessage - простое сообщение
   * @param id              - id сущности, по которому выполнялся поиск
   * @param entity          - ненайденная сущность
   */
  public DataNotFoundResponse(String message, String friendlyMessage, UUID id, String entity) {
    super(message, friendlyMessage);
    this.id = id;
    this.entity = entity;
  }

  /**
   * конструктор на основе исключения.
   *
   * @param ex - исключение, на которое нужно сформировать ответ
   */
  public DataNotFoundResponse(DataNotFoundException ex) {
    super(ex.getMessage(), ex.getFriendlyMessage());
    this.id = ex.getId();
    this.entity = ex.getEntity();
  }
}
