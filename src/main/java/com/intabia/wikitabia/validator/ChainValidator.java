package com.intabia.wikitabia.validator;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, позволяющий выполнить валидацию в определенной последовательности.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChainValidator implements Validator {
  private static final String VALIDATOR_IS_NULL =
      "Добавляемый в цепочку валидатор не должен быть null";
  private final List<Validator> chain = new ArrayList<>();

  public static ChainValidator create() {
    return new ChainValidator();
  }

  /**
   * Добавить валидатор в цепочку валидации.
   *
   * @param validator добавляемый валидатор
   * @return возвращает цепочку валидации с добавленным валидатором
   * @throws IllegalArgumentException если добавляемый валидатор имеет значение null
   */
  public ChainValidator add(Validator validator) {
    if (validator == null) {
      throw new IllegalArgumentException(VALIDATOR_IS_NULL);
    }

    chain.add(validator);
    return this;
  }

  @Override
  public void validate() {
    for (Validator validator : chain) {
      validator.validate();
    }
  }
}
