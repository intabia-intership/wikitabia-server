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
  private static final String CLASS_IS_NULL = "Переводимый класс не должен быть null";
  private static final Map<String, String> userFriendlyNames = new HashMap<>();

  /**
   * Добавить понятное для пользователя название класса в словарь.
   *
   * @param className        название класса
   * @param userFriendlyName понятное для пользователя название этого класса
   */
  public static void add(String className, String userFriendlyName) {
    userFriendlyNames.put(className, userFriendlyName);
  }

  /**
   * Получить понятное для пользователя название класса.
   *
   * @param classToTranslate класс, для которого необходимо получить
   *                         понятное для пользователя название
   * @return возвращает понятное для пользователя название класса
   * @throws IllegalArgumentException если classToTranslate имеет значение null
   * @throws NoSuchElementException   если не найдено понятное для пользователя название
   */
  public static String getUserFriendlyName(Class<?> classToTranslate) {
    if (classToTranslate == null) {
      throw new IllegalArgumentException(CLASS_IS_NULL);
    }

    String userFriendlyName = userFriendlyNames.get(classToTranslate.getSimpleName());
    if (userFriendlyName == null) {
      throw new NoSuchElementException(
          String.format(NO_USER_FRIENDLY_NAME_FORMAT, classToTranslate.getName()));
    }
    return userFriendlyName;
  }
}
