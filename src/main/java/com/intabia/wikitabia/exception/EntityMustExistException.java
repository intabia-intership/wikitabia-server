package com.intabia.wikitabia.exception;

/**
 * Исключение, выбрасывается в случае
 * нарушения правила необходимости
 * существования сущности, указанной в поле.
 */
public class EntityMustExistException extends InvalidBodyException {
  private static final String MUST_EXIST_RULE_FORMAT = "%s должен существовать";

  /**
   * Конструктор.
   *
   * @param requestDtoClass          класс проблемного dto
   * @param userFriendlyPropertyName понятное для пользователя название поля,
   *                                 возможно с указанием сущности, которой оно принадлежит
   * @param wrongValue               неправильное значение, вызвавшее ошибку
   */
  public EntityMustExistException(Class<?> requestDtoClass, String userFriendlyPropertyName,
                                  Object wrongValue) {
    super(requestDtoClass, wrongValue,
        String.format(MUST_EXIST_RULE_FORMAT, userFriendlyPropertyName));
  }
}
