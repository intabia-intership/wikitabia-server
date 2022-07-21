package com.intabia.wikitabia.model.util;

import com.intabia.wikitabia.model.annotation.UserFriendlyName;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * класс, хранящий приветливые названия классов,
 * помеченных аннотацией {@link UserFriendlyName @UserFriendlyName}.
 */
public class UserFriendlyNameTranslationTool {
  private static final String NO_USER_FRIENDLY_NAME_FORMAT =
      "Нет понятного для пользователя названия для сущности %s";
  private static final Map<String, String> userFriendlyNames = new HashMap<>();

  public static void add(String entityName, String userFriendlyName) {
    userFriendlyNames.put(entityName, userFriendlyName);
  }

  /**
   * получить понятное для пользователя название класса.
   *
   * @param entity - класс сущности
   * @return возвращает понятное для пользователя название класса
   * @throws NoSuchElementException - если не найдено понятное для пользователя название
   */
  public static String getFriendlyName(Class<?> entity) {
    String userFriendlyName = userFriendlyNames.get(entity.getSimpleName());
    if (userFriendlyName == null) {
      throw new NoSuchElementException(
          String.format(NO_USER_FRIENDLY_NAME_FORMAT, entity.getName()));
    }
    return userFriendlyName;
  }
}
