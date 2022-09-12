package com.intabia.wikitabia.validator.common.info;

import java.util.Optional;
import java.util.function.Function;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Содержит функцию, позволяющую найти сущность по уникальному полю.
 *
 * @param <F> тип уникального поля
 * @param <E> тип сущности
 */
@Getter
@SuperBuilder(builderMethodName = "exBuilder")
public class ExistenceByUniqueFieldInfo<F, E> extends FriendlyExceptionInfo {
  /**
   * функция поиска сущности по уникальному полю.
   */
  protected final Function<F, Optional<E>> findFunc;
}
