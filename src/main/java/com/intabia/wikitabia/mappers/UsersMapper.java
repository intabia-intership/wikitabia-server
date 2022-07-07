package com.intabia.wikitabia.mappers;

import com.intabia.wikitabia.entities.UserEntity;
import org.mapstruct.Mapper;
import com.intabia.wikitabia.dto.UserDto;

/**
 * маппер сущностей user.
 */
@Mapper(componentModel = "spring", uses = {AuthoritiesMapper.class})
public interface UsersMapper {

  UserEntity dtoToEntity(UserDto userDto);

  UserDto entityToDto(UserEntity userEntity);
}
