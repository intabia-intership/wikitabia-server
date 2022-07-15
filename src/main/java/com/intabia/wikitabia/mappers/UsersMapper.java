package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.model.UserEntity;
import org.mapstruct.Mapper;
import com.intabia.wikitabia.dto.UserDto;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * маппер сущностей user.
 */
@Mapper(componentModel = "spring", uses = {AuthoritiesMapper.class})
public interface UsersMapper {

  UserEntity dtoToEntity(UserDto userDto);

  @Mapping(target = "authorities", ignore = true)
  UserEntity dtoToEntity(CreateUserDto createUserDto);

  @Mapping(target = "authorities", ignore = true)
  UserEntity dtoToEntity(UpdateUserDto updateUserDto);

  UserDto entityToDto(UserEntity userEntity);

  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget UserEntity userEntity, UpdateUserDto updateUserDto);
}
