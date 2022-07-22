package com.intabia.wikitabia.mappers.entity;

import com.intabia.wikitabia.dto.TagDto;
import com.intabia.wikitabia.model.TagEntity;
import org.mapstruct.Mapper;

/**
 * маппер сущностей tags.
 */
@Mapper(componentModel = "spring")
public interface TagsMapper {

  TagEntity dtoToEntity(TagDto tagDto);

  TagDto entityToDto(TagEntity tagEntity);
}
