package com.intabia.wikitabia.mappers.entity;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.model.ResourceEntity;
import org.mapstruct.Mapper;

/**
 * маппер для сущностей resources.
 */
@Mapper(componentModel = "spring")
public interface ResourcesMapper {

  ResourceEntity dtoToEntity(ResourceDto resourceDto);

  ResourceDto entityToDto(ResourceEntity resourceEntity);
}
