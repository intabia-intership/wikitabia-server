package com.intabia.wikitabia.dao;

import com.intabia.wikitabia.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthoritiesDao extends JpaRepository<AuthorityEntity, UUID> {
    Optional<AuthorityEntity> findAuthorityEntityByName(String name);
}
