package com.intabia.wikitabia.exception;

import lombok.Getter;

/**
 * Исключение, выбрасывается в случае
 * некорректных данных в теле запроса.
 */
@Getter
public class InvalidBodyException extends BadRequestException {
  private static final String MESSAGE_FORMAT =
      "Поле тела запроса %s имеет неправильное значение %s. %s";

  /**
   * Конструктор.
   * Автоматически генерирует подробное сообщение об ошибке.
   * Автоматически генерирует понятное для пользователя сообщение.
   *
   * @param requestDtoClass класс проблемного dto
   * @param wrongValue      неправильное значение, вызвавшее ошибку
   * @param rule            правило для задания значения
   */
  public InvalidBodyException(Class<?> requestDtoClass, Object wrongValue, String rule) {
    super(requestDtoClass.getSimpleName(), wrongValue, rule,
        String.format(MESSAGE_FORMAT, requestDtoClass.getSimpleName(), wrongValue, rule),
        rule);
  }
}
