package com.intabia.wikitabia.services.interfaces;

import com.intabia.wikitabia.dto.UserDto;
import java.util.UUID;

/**
 * интерфес слоя сервисов для работы с user.
 */
public interface UserService {
  UserDto createUser(UserDto userDto);

  UserDto updateUser(UserDto userDto);

  void deleteUser(UUID id);

  UserDto getUser(UUID id);

  UserDto authorisation(String userName, String userPassword);

  void addLogin(UserDto user);

  UserDto findUserByLogin(String login);
}
