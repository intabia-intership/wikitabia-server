package com.intabia.wikitabia.repository;

import com.intabia.wikitabia.model.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * слой DAO для работы с таблицей authority в БД.
 */
public interface AuthoritiesDao extends JpaRepository<AuthorityEntity, UUID> {
    Optional<AuthorityEntity> findAuthorityEntityByName(String name);
}
