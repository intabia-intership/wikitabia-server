package com.intabia.wikitabia.services;

import com.intabia.wikitabia.dto.UserDto;
import java.util.UUID;

/**
 * интерфес слоя сервисов для работы с user.
 */
public interface UserService {
  UserDto saveUser(UserDto userDto);

  UserDto updateUser(UserDto userDto);

  void deleteUser(UUID id);

  UserDto getUser(UUID id);

  void addLogin(UserDto user);

  UserDto findUserByLogin(String login);
}
