package com.intabia.wikitabia.exception;

import com.intabia.wikitabia.model.util.UserFriendlyNameTranslationTool;
import java.util.UUID;
import lombok.Getter;

/**
 * Исключение, выбрасывается в случае отсутствия
 * возможности найти данные в базе данных.
 */
@Getter
public class EntityNotFoundException extends FriendlyRuntimeException {
  private static final String MESSAGE_FORMAT = "Сущность %s с id %s не найдена в базе данных";
  private static final String USER_FRIENDLY_MESSAGE_FORMAT = "%s не существует";

  /**
   * Id сущности, по которому выполнялся поиск.
   */
  private final UUID entityId;

  /**
   * Имя класса ненайденной сущности.
   */
  private final String entityClassName;

  /**
   * Конструктор.
   * Автоматически генерирует подробное сообщение об ошибке.
   * Автоматически генерирует понятное для пользователя сообщение.
   *
   * @param entityClass класс ненайденной сущности
   * @param entityId    id сущности, по которому выполнялся поиск
   */
  public EntityNotFoundException(Class<?> entityClass, UUID entityId) {
    super(String.format(MESSAGE_FORMAT, entityClass.getName(), entityId),
        String.format(USER_FRIENDLY_MESSAGE_FORMAT,
            UserFriendlyNameTranslationTool.getUserFriendlyName(entityClass)));
    this.entityClassName = entityClass.getSimpleName();
    this.entityId = entityId;
  }
}
