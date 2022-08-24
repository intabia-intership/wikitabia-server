package com.intabia.wikitabia.repository;


import com.intabia.wikitabia.model.ResourceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Слой DAO для работы таблицей resources в БД.
 */

public interface ResourcesDao extends JpaRepository<ResourceEntity, UUID>,
    JpaSpecificationExecutor<ResourceEntity> {
}
