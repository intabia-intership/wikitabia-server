package com.intabia.wikitabia.exception;

/**
 * Исключение, выбрасывается в случае
 * нарушения правила уникальности поля.
 */
public class UniqueFieldException extends InvalidBodyException {
  private static final String UNIQUE_RULE_FORMAT = "%s не может повторяться";

  /**
   * Конструктор.
   *
   * @param requestDtoClass          класс проблемного dto
   * @param userFriendlyPropertyName понятное для пользователя название поля,
   *                                 возможно с указанием сущности, которой оно принадлежит
   * @param wrongValue               неправильное значение, вызвавшее ошибку
   */
  public UniqueFieldException(Class<?> requestDtoClass, String userFriendlyPropertyName,
                              Object wrongValue) {
    super(requestDtoClass, wrongValue,
        String.format(UNIQUE_RULE_FORMAT, userFriendlyPropertyName));
  }
}
