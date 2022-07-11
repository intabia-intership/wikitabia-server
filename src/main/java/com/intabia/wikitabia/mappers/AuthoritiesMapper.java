package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * маппер сущностей authority.
 */
@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
    AuthorityEntity dtoToEntity(AuthorityDto authorityDto);

    AuthorityDto entityToDto(AuthorityEntity authorityEntity);

    void updateEntity(@MappingTarget AuthorityEntity authorityEntity, AuthorityDto authorityDto);
}
