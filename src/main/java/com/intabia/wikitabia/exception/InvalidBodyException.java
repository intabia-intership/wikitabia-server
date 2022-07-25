package com.intabia.wikitabia.exception;

import lombok.Getter;

/**
 * исключение времени выполнения,
 * выбрасывается в случае
 * некорректных данные в теле запроса.
 */
@Getter
public class InvalidBodyException extends BadRequestException {
  private final String[] propertyChain;
  private final Object actualValue;
  private final String rule;

  private InvalidBodyException(String[] propertyChain, Object actualValue, String rule,
                               String message, String friendlyMessage) {
    super(message, friendlyMessage);
    this.propertyChain = propertyChain;
    this.actualValue = actualValue;
    this.rule = rule;
  }

  /**
   * создать экземпляр класса.
   *
   * @param propertyChain   - последовательность названий полей вплоть до ошибочного поля
   * @param actualValue     - неправильное значение, вызвавшее ошибку
   * @param rule            - правило для задания значения
   * @param message         - полное сообщение
   * @param friendlyMessage - простое сообщение
   * @return возвращает экземпляр класса;
     null если propertyChain == null, пустой propertyChain или rule = null
   */
  public static InvalidBodyException create(String[] propertyChain, Object actualValue, String rule,
                                            String message, String friendlyMessage) {
    if (propertyChain == null || propertyChain.length == 0 || rule == null) {
      return null;
    }

    return new InvalidBodyException(propertyChain, actualValue, rule, message, friendlyMessage);
  }

  /**
   * создать экземпляр класса.
   * Автоматически генерирует полное сообщение.
   *
   * @param propertyChain   - последовательность названий полей вплоть до ошибочного поля
   * @param actualValue     - неправильное значение, вызвавшее ошибку
   * @param rule            - правило для задания значения
   * @param friendlyMessage - простое сообщение
   * @return возвращает экземпляр класса;
     null если propertyChain == null, пустой propertyChain или rule = null
   */
  public static InvalidBodyException create(String[] propertyChain, Object actualValue, String rule,
                                            String friendlyMessage) {
    if (propertyChain == null || propertyChain.length == 0 || rule == null) {
      return null;
    }

    String chain = createChain(propertyChain);
    String message =
        "Поле " + chain + " имеет неправильное значение " + actualValue + ". " + rule;
    return new InvalidBodyException(propertyChain, actualValue, rule, message, friendlyMessage);
  }

  /**
   * создать экземпляр класса.
   * Автоматически генерирует полное сообщение.
   * Автоматически генерирует простое сообщение.
   *
   * @param propertyChain   - последовательность названий полей вплоть до ошибочного поля
   * @param actualValue     - неправильное значение, вызвавшее ошибку
   * @param rule            - правило для задания значения
   * @return возвращает экземпляр класса;
     null если propertyChain == null, пустой propertyChain или rule = null
   */
  public static InvalidBodyException create(String[] propertyChain, Object actualValue,
                                            String rule) {
    if (propertyChain == null || propertyChain.length == 0 || rule == null) {
      return null;
    }

    String chain = createChain(propertyChain);
    String message =
        "Поле " + chain + " имеет неправильное значение " + actualValue + ". " + rule;
    String lastProperty = propertyChain[propertyChain.length - 1];
    String friendlyMessage = lastProperty + ": " + rule;
    return new InvalidBodyException(propertyChain, actualValue, rule, message, friendlyMessage);
  }

  private static String createChain(String[] propertyChain) {
    if (propertyChain == null || propertyChain.length == 0) {
      return null;
    }

    StringBuilder chain = new StringBuilder();
    int i;
    for (i = 0; i < propertyChain.length - 1; i++) {
      chain.append(propertyChain[i]).append("->");
    }
    chain.append(propertyChain[i]);

    return chain.toString();
  }
}
