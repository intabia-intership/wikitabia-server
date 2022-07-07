package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.entities.AuthorityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
    AuthorityEntity dtoToEntity(AuthorityDto authorityDto);

    AuthorityDto entityToDto(AuthorityEntity authorityEntity);
}
