package com.intabia.wikitabia.validator.common;

import com.intabia.wikitabia.model.EntityWithUuid;
import com.intabia.wikitabia.validator.common.info.ExistenceByIdInfo;
import java.util.UUID;

/**
 * валидатор, проверяющий наличие сущности с указанным id в базе данных.
 * Если сущности не существует, то кидает исключение.
 *
 * @param <E> тип сущности
 */
public class ExistenceByIdValidator<E extends EntityWithUuid>
    extends ExistenceByUniqueFieldValidator<UUID, E> {
  public ExistenceByIdValidator(UUID fieldValue, ExistenceByIdInfo<E> info) {
    super(fieldValue, info);
  }
}
