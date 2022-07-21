package com.intabia.wikitabia.mappers.entity;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * маппер сущностей user.
 */
@Mapper(componentModel = "spring", uses = {AuthoritiesMapper.class})
public interface UsersMapper {

  UserEntity dtoToEntity(UserResponseDto userResponseDto);

  @Mapping(target = "authorities", ignore = true)
  UserEntity dtoToEntity(UserCreateRequestDto userCreateRequestDto);

  @Mapping(target = "authorities", ignore = true)
  UserEntity dtoToEntity(UserUpdateRequestDto userUpdateRequestDto);

  UserResponseDto entityToDto(UserEntity userEntity);

  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget UserEntity userEntity,
                    UserUpdateRequestDto userUpdateRequestDto);
}
