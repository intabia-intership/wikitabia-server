package com.intabia.wikitabia.validator.common.info;

import com.intabia.wikitabia.exception.FriendlyRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Содержит исключение типа {@link FriendlyRuntimeException}, необходимое валидатору.
 */
@Getter
@SuperBuilder
@RequiredArgsConstructor
public abstract class FriendlyExceptionInfo {
  protected final FriendlyRuntimeException exception;
}
