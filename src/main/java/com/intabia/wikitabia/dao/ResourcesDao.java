package com.intabia.wikitabia.dao;


import java.util.UUID;

import com.intabia.wikitabia.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Слой DAO для работы таблицей resources в БД.
 */

public interface ResourcesDao extends JpaRepository<ResourceEntity, UUID> ,
    JpaSpecificationExecutor<ResourceEntity> {
}
