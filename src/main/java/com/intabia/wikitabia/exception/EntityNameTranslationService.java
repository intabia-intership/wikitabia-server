package com.intabia.wikitabia.exception;

import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.ResourceEntity;
import com.intabia.wikitabia.model.TagEntity;
import com.intabia.wikitabia.model.UserEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * класс, переводящий названия сущностей на русский язык.
 */
public class EntityNameTranslationService {
  private static final Map<String, String> dictionary = new HashMap<>();

  static {
    dictionary.put(AuthorityEntity.class.getSimpleName(), "Роль");
    dictionary.put(ResourceEntity.class.getSimpleName(), "Ресурс");
    dictionary.put(TagEntity.class.getSimpleName(), "Тег");
    dictionary.put(UserEntity.class.getSimpleName(), "Пользователь");
  }

  public static String getRussianName(Class<?> entity) {
    String englishName = entity.getSimpleName();
    return dictionary.get(englishName);
  }
}
