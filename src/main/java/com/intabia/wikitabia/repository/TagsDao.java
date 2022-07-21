package com.intabia.wikitabia.repository;

import com.intabia.wikitabia.model.TagEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO класс для работы с таблицей tags в БД.
 */

public interface TagsDao extends JpaRepository<TagEntity, UUID> {
  TagEntity findByName(String name);
}
