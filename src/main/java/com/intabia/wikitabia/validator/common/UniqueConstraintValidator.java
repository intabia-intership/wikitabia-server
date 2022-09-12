package com.intabia.wikitabia.validator.common;

import com.intabia.wikitabia.model.EntityWithUuid;
import com.intabia.wikitabia.validator.AbstractValidator;
import com.intabia.wikitabia.validator.common.info.UniqueConstraintInfo;
import java.util.Optional;

/**
 * валидатор, проверяющий, не нарушает ли значение поля сущности ограничение уникальности.
 *
 * @param <F> тип поля
 * @param <E> тип сущности
 */
public class UniqueConstraintValidator<F, E extends EntityWithUuid> extends
    AbstractValidator<F, UniqueConstraintInfo<F, E>> {
  public UniqueConstraintValidator(F fieldVal, UniqueConstraintInfo<F, E> info) {
    super(fieldVal, info);
  }

  @Override
  public void validate() {
    Optional<E> other = info.getFindFunc().apply(object);
    if (other.isPresent() && !other.get().getId().equals(info.getId())) {
      throw info.getException();
    }
  }
}
