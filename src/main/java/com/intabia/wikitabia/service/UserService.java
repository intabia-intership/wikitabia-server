package com.intabia.wikitabia.service;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import java.util.UUID;

/**
 * интерфейс слоя сервисов для работы с user.
 */
public interface UserService {
  /**
   * создание нового пользователя.
   *
   * @param userCreateRequestDto - создаваемый пользователь
   * @return возвращает созданного пользователя
   */
  UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto);

  /**
   * модификация пользователя по id.
   *
   * @param userUpdateRequestDto - новый пользователь
   * @param id - id пользователя
   * @return возвращает измененного пользователя
   */
  UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto, UUID id);

  /**
   * удаление пользователя по id.
   *
   * @param id - id пользователя
   */
  UUID deleteUser(UUID id);

  /**
   * получение пользователя по id.
   *
   * @param id - id пользователя
   * @return возвращает найденного пользователя
   */
  UserResponseDto getUser(UUID id);

  /**
   * назначение пользователю telegram-логина.
   *
   * @param user - пользователь, которому необходимо назначить telegram-логин
   * @return возвращает измененного пользователя
   */
  UserResponseDto addLogin(UserUpdateRequestDto user, UUID id);
}
