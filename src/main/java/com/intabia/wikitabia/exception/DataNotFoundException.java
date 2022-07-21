package com.intabia.wikitabia.exception;

import com.intabia.wikitabia.model.util.EntityNameTranslationTool;
import java.util.UUID;
import lombok.Getter;

/**
 * исключение времени выполнения,
 * выбрасывается в случае отсутствия
 * возможности найти данные в базе данных.
 */
@Getter
public class DataNotFoundException extends FriendlyRuntimeException {
  /**
   * id, по которому выполнялся поиск.
   */
  private final UUID id;

  /**
   * ненайденная сущность.
   */
  private final String entity;

  /**
   * конструктор.
   *
   * @param entity      - ненайденная сущность
   * @param id          - id, по которому выполнялся поиск
   * @param msg         - полное сообщение
   * @param friendlyMsg - простое сообщение
   */
  public DataNotFoundException(Class<?> entity, UUID id, String msg, String friendlyMsg) {
    super(msg, friendlyMsg);
    this.entity = entity.getSimpleName();
    this.id = id;
  }

  /**
   * конструктор.
   * Автоматически генерирует полное сообщение.
   *
   * @param entity      - ненайденная сущность
   * @param id          - id, по которому выполнялся поиск
   * @param friendlyMsg - простое сообщение
   */
  public DataNotFoundException(Class<?> entity, UUID id, String friendlyMsg) {
    super(generateMessage(entity, id), friendlyMsg);
    this.entity = entity.getSimpleName();
    this.id = id;
  }

  /**
   * конструктор.
   * Автоматически генерирует полное сообщение.
   * Автоматически генерирует простое сообщение.
   *
   * @param entity      - ненайденная сущность
   * @param id          - id, по которому выполнялся поиск
   */
  public DataNotFoundException(Class<?> entity, UUID id) {
    super(generateMessage(entity, id), generateFriendlyMessage(entity, id));
    this.entity = entity.getSimpleName();
    this.id = id;
  }

  private static String generateMessage(Class<?> entity, UUID id) {
    return
        String.format("Сущность %s с id %s не найдена в базе данных", entity.getSimpleName(), id);
  }

  private static String generateFriendlyMessage(Class<?> entity, UUID id) {
    return String.format("%s не существует", EntityNameTranslationTool.getRussianName(entity));
  }
}
