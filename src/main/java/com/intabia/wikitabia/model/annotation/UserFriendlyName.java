package com.intabia.wikitabia.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотация, позволяющая указать понятное для пользователя название класса.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserFriendlyName {
  /**
   * понятное для пользователя название класса.
   *
   * @return возвращает понятное для пользователя название класса
   */
  String value();
}
