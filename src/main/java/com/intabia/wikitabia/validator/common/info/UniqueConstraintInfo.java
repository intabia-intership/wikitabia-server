package com.intabia.wikitabia.validator.common.info;

import com.intabia.wikitabia.model.EntityWithUuid;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Содержит id сущности, чья уникальность проверяется.
 *
 * @param <F> тип уникального поля
 * @param <E> тип сущности, чье поле проверяется
 */
@Getter
@SuperBuilder
public class UniqueConstraintInfo<F, E extends EntityWithUuid> extends
    ExistenceByUniqueFieldInfo<F, E> {
  /**
   * id проверяемой сущности. Если наличие сущности в бд не предполагается, то null
   */
  private final UUID id;
}
