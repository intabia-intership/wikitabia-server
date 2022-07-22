package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import javax.validation.Valid;
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
  @SecurityRequirement(name = "wikitabia basic")
  @SecurityRequirement(name = "wikitabia keycloak")
  @Operation(summary = "Получить пользователя по id")
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
  @SecurityRequirement(name = "wikitabia basic")
  @SecurityRequirement(name = "wikitabia keycloak")
  @Operation(summary = "Удалить пользователя по id")
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
  @SecurityRequirement(name = "wikitabia basic")
  @SecurityRequirement(name = "wikitabia keycloak")
  @Operation(summary = "Изменить пользователя по id")
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
  @Operation(summary = "Создать нового пользователя")
  @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
    return userService.createUser(createUserDto);
  }

  /**
   * назачение пользователю telegram-логина.
   *
   * @param user - пользователь, которому необходимо назначить telegram-логин
   */
  @Operation(summary = "Назначить пользователю телеграм-логин")
  @PutMapping(value = "/telegram-login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void addTelegramLogin(@RequestBody UserDto user) {
    userService.addLogin(user);

  }
}
