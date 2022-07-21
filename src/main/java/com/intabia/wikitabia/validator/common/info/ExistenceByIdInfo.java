package com.intabia.wikitabia.validator.common.info;

import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Содержит функцию, позволяющую найти сущность по id.
 *
 * @param <E> тип сущности
 */
@Getter
@SuperBuilder(builderMethodName = "exIdBuilder")
public class ExistenceByIdInfo<E> extends ExistenceByUniqueFieldInfo<UUID, E> {
}
