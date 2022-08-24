package com.intabia.wikitabia.mappers.entity;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * маппер сущностей authority.
 */
@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
  AuthorityEntity dtoToEntity(AuthorityRequestDto authorityRequestDto);

  AuthorityResponseDto entityToDto(AuthorityEntity authorityEntity);

  void updateEntity(@MappingTarget AuthorityEntity authorityEntity,
                    AuthorityRequestDto authorityRequestDto);
}
