package com.intabia.wikitabia.validator;

import lombok.RequiredArgsConstructor;

/**
 * валидатор, выполняющий проверку в отношении {@link AbstractValidator#object}
 * с использованием информации, содержащейся в {@link AbstractValidator#info}.
 *
 * @param <O> тип объекта валидации
 * @param <I> тип информации об объекте валидации
 */
@RequiredArgsConstructor
public abstract class AbstractValidator<O, I> implements Validator {
  /**
   * объект валидации.
   */
  protected final O object;
  /**
   * информация об объекте валидации.
   */
  protected final I info;

  @Override
  public abstract void validate();
}
