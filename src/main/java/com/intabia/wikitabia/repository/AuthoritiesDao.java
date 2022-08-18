package com.intabia.wikitabia.repository;

import com.intabia.wikitabia.model.AuthorityEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * слой DAO для работы с таблицей authority в БД.
 */
public interface AuthoritiesDao extends JpaRepository<AuthorityEntity, UUID> {
  Optional<AuthorityEntity> findAuthorityEntityByName(String name);

  boolean existsByName(String name);
}
