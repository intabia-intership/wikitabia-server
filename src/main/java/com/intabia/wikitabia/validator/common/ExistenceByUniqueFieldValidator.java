package com.intabia.wikitabia.validator.common;

import com.intabia.wikitabia.model.EntityWithUuid;
import com.intabia.wikitabia.validator.AbstractValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByUniqueFieldInfo;
import java.util.Optional;

/**
 * валидатор, проверяющий наличие сущности с указанным уникальным полем в базе данных.
 * Если сущности не существует, то кидает исключение.
 *
 * @param <F> тип поля
 * @param <E> тип сущности
 */
public class ExistenceByUniqueFieldValidator<F, E extends EntityWithUuid> extends
    AbstractValidator<F, ExistenceByUniqueFieldInfo<F, E>> {
  public ExistenceByUniqueFieldValidator(F fieldValue, ExistenceByUniqueFieldInfo<F, E> info) {
    super(fieldValue, info);
  }

  @Override
  public void validate() {
    Optional<E> opt = info.getFindFunc().apply(object);
    if (opt.isEmpty()) {
      throw info.getException();
    }
  }
}
