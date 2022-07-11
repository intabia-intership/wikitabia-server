package com.intabia.wikitabia.services.service;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import java.util.UUID;

/**
 * интерфес слоя сервисов для работы с user.
 */
public interface UserService {
  /**
   * создание нового пользователя.
   *
   * @param createUserDto - создаваемый пользователь
   * @return возвращает созданного пользователя
   */
  UserDto createUser(CreateUserDto createUserDto);

  /**
   * модификация пользователя по id.
   *
   * @param updateUserDto - новый пользователь
   * @param id - id пользователя
   * @return возвращает измененного пользователя
   */
  UserDto updateUser(UpdateUserDto updateUserDto, UUID id);

  /**
   * удаление пользователя по id.
   *
   * @param id - id пользователя
   */
  void deleteUser(UUID id);

  /**
   * получение пользователя по id.
   *
   * @param id - id пользователя
   * @return возвращает найденного пользователя
   */
  UserDto getUser(UUID id);

  /**
   * метод для авторизации. обращение из frontend.
   *
   * @param userName принимает логин пользователя
   * @param userPassword  принимает пароль пользователя и хеширует его
   * @return возвращает boolean нашлось ли совпадение в БД
   */
  UserDto authorization(String userName, String userPassword);

  /**
   * назачение пользователю telegram-логина.
   *
   * @param user - пользователь, которому необходимо назначить telegram-логин
   */
  void addLogin(UserDto user);

  /**
   * получение пользователя по логину.
   *
   * @param login - логин пользователя
   * @return возвращает найденного пользователя
   */
  UserDto findUserByLogin(String login);
}
