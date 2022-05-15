package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.entities.TagEntity;
import org.mapstruct.Mapper;
import com.intabia.wikitabia.dto.TagDto;

/**
 * маппер сущностей tags.
 */
@Mapper(componentModel = "spring")
public interface TagsMapper {

  TagEntity dtoToEntity(TagDto tagDto);

  TagDto entityToDto(TagEntity tagEntity);
}
