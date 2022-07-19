package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {
  private final UserService userService;

  /**
   * получение пользователя по id.
   *
   * @param id - id пользователя
   * @return возвращает найденного пользователя
   */
  @GetMapping("/user/{id}")
  public UserDto getUser(@PathVariable UUID id) {
    return userService.getUser(id);
  }

  /**
   * удаление пользователя по id.
   *
   * @param id - id пользователя
   * @return возвращает id удаленного пользователя
   */
  @DeleteMapping("/user/{id}")
  public UUID deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return id;
  }

  /**
   * модификация пользователя по id.
   *
   * @param updateUserDto - новый пользователь
   * @param id - id пользователя
   * @return возвращает измененного пользователя
   */
  @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto updateUser(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable UUID id) {
    return userService.updateUser(updateUserDto, id);
  }

  /**
   * создание нового пользователя.
   *
   * @param createUserDto - создаваемый пользователь
   * @return возвращает созданного пользователя
   */
  @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
    return userService.createUser(createUserDto);
  }

  /**
   * назачение пользователю telegram-логина.
   *
   * @param user - пользователь, которому необходимо назначить telegram-логин
   */
  @PutMapping(value = "/telegram-login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void addTelegramLogin(@RequestBody UserDto user) {
    userService.addLogin(user);

  }
}
