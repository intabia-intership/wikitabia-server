package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.controller.annotation.NoSecurityRequirements;
import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользователь API", description = "API для операций над пользователями")
public class UserRestController {
  private final UserService userService;

  @Operation(summary = "Получить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь найден")
  @GetMapping("/user/{id}")
  public UserDto getUser(
      @Parameter(description = "id пользователя, по которому выполняется поиск")
      @PathVariable UUID id) {
    return userService.getUser(id);
  }

  @Operation(summary = "Удалить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь удален")
  @DeleteMapping("/user/{id}")
  public UUID deleteUser(
      @Parameter(description = "id пользователя, которого необходимо удалить")
      @PathVariable UUID id) {
    userService.deleteUser(id);
    return id;
  }

  @Operation(summary = "Изменить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь изменен")
  @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto updateUser(
      @RequestBody @Valid UpdateUserDto updateUserDto,
      @Parameter(description = "id пользователя, которого необходимо изменить")
      @PathVariable UUID id) {
    return userService.updateUser(updateUserDto, id);
  }

  @Operation(summary = "Создать нового пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь создан")
  @NoSecurityRequirements
  @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto createUser(
      @RequestBody @Valid CreateUserDto createUserDto) {
    return userService.createUser(createUserDto);
  }

  @Operation(summary = "Назначить пользователю телеграм-логин", deprecated = true)
  @ApiResponse(responseCode = "200", description = "Пользователю назначен логин в telegram")
  @NoSecurityRequirements
  @PutMapping(value = "/telegram-login", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public void addTelegramLogin(
      @RequestBody UserDto user) {
    userService.addLogin(user);
  }
}
