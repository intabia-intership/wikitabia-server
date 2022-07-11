package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.model.ResourceEntity;
import org.mapstruct.Mapper;
import com.intabia.wikitabia.dto.ResourceDto;

/**
 * маппер для сущностей resources.
 */
@Mapper(componentModel = "spring")
public interface ResourcesMapper {

  ResourceEntity dtoToEntity(ResourceDto resourceDto);

  ResourceDto entityToDto(ResourceEntity resourceEntity);
}
