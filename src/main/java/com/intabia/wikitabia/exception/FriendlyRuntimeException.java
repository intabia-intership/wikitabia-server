package com.intabia.wikitabia.exception;

import lombok.Getter;

/**
 * Исключение, содержит простое и понятное для пользователя сообщение.
 */
@Getter
public abstract class FriendlyRuntimeException extends RuntimeException {
  /**
   * Понятное для конечного пользователя сообщение,
   * не содержащее данных о внутреннем устройстве приложения.
   */
  private final String userFriendlyMessage;

  public FriendlyRuntimeException(String message, String userFriendlyMessage) {
    super(message);
    this.userFriendlyMessage = userFriendlyMessage;
  }
}
