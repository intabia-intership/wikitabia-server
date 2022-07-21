package com.intabia.wikitabia.dto.util;

/**
 * класс с правилами для полей в dto классах.
 */
public class FieldRulesConstant {
  public static final String UNIQUE_ROLE_RULE = "Название роли должно быть уникальным";
  public static final String UNIQUE_LOGIN_RULE = "Логин должен быть уникальным";
  public static final String UNIQUE_TELEGRAM_LOGIN_RULE =
      "Логин в telegram должен быть уникальным";
  public static final String ROLE_MUST_EXIST_RULE =
      "Роль добавляемого пользователя должна существовать";
}
