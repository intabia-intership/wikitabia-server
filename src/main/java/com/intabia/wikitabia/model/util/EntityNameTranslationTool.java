package com.intabia.wikitabia.model.util;

import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.ResourceEntity;
import com.intabia.wikitabia.model.TagEntity;
import com.intabia.wikitabia.model.UserEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * класс, переводящий названия сущностей на русский язык.
 */
public class EntityNameTranslationTool {
  private static final Map<String, String> dictionary = new HashMap<>();

  static {
    dictionary.put(AuthorityEntity.class.getSimpleName(), "Роль");
    dictionary.put(ResourceEntity.class.getSimpleName(), "Ресурс");
    dictionary.put(TagEntity.class.getSimpleName(), "Тег");
    dictionary.put(UserEntity.class.getSimpleName(), "Пользователь");
  }

  /**
   * получить перевод названия сущности на русский язык.
   *
   * @param entity - класс сущности
   * @return возвращает название сущности на русском языке
   * @throws NoSuchElementException - если в словаре нет перевода
   */
  public static String getRussianName(Class<?> entity) {
    String englishName = entity.getSimpleName();
    String russianName = dictionary.get(englishName);
    if (russianName == null) {
      throw new NoSuchElementException(
          String.format("В словаре нет перевода для сущности %s", entity));
    }
    return russianName;
  }
}
