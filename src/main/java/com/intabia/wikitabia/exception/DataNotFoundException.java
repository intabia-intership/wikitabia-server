package com.intabia.wikitabia.exception;

import java.util.UUID;
import lombok.Getter;

/**
 * исключение времени выполнения,
 * выбрасывается в случае отсутствия
 * возможности найти данные в базе данных.
 */
@Getter
public class DataNotFoundException extends CustomRuntimeException {
  private final UUID id;
  private final String entity;

  private DataNotFoundException(Class<?> entity, UUID id, String msg, String friendlyMsg) {
    super(msg, friendlyMsg);
    this.entity = entity.getSimpleName();
    this.id = id;
  }

  /**
   * создать экземпляр класса.
   *
   * @param entity - ненайденная сущность
   * @param id - id, по которому выполнялся поиск
   * @param msg - полное сообщение
   * @param friendlyMsg - простое сообщение
   * @return возвращает экземпляр класса;
     null если entity == null или id == null
   */
  public static DataNotFoundException create(Class<?> entity, UUID id, String msg,
                                             String friendlyMsg) {
    if (entity == null || id == null) {
      return null;
    }

    return new DataNotFoundException(entity, id, msg, friendlyMsg);
  }

  /**
   * создать экземпляр класса.
   * Автоматически генерирует полное сообщение.
   *
   * @param entity - ненайденная сущность
   * @param id - id, по которому выполнялся поиск
   * @param friendlyMsg - простое сообщение
   * @return возвращает экземпляр класса;
     null если entity == null или id == null
   */
  public static DataNotFoundException create(Class<?> entity, UUID id, String friendlyMsg) {
    if (entity == null || id == null) {
      return null;
    }

    String msg = "Сущность " + entity.getSimpleName() + " с id " + id + " не найдена в базе данных";
    return new DataNotFoundException(entity, id, msg, friendlyMsg);
  }

  /**
   * создать экземпляр класса.
   * Автоматически генерирует полное сообщение.
   * Автоматически генерирует простое сообщение.
   *
   * @param entity - ненайденная сущность
   * @param id - id, по которому выполнялся поиск
   * @return возвращает экземпляр класса;
     null если entity == null или id == null
   */
  public static DataNotFoundException create(Class<?> entity, UUID id) {
    if (entity == null || id == null) {
      return null;
    }

    String msg = "Сущность " + entity.getSimpleName() + " с id " + id + " не найдена в базе данных";
    String friendlyMsg = EntityNameTranslationService.getRussianName(entity) + " не существует";
    return new DataNotFoundException(entity, id, msg, friendlyMsg);
  }
}
