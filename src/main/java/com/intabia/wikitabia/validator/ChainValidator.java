package com.intabia.wikitabia.validator;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * класс, позволяющий выполнить валидацию в определенной последовательности.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChainValidator implements Validator {
  private final List<Validator> chain = new ArrayList<>();

  public static ChainValidator create() {
    return new ChainValidator();
  }

  public ChainValidator add(Validator validator) {
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
