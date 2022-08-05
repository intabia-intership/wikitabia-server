package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.controller.annotation.NoSecurityRequirements;
import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j(topic = "com.intabia.wikitabia.logger")
public class UserRestController {
  private final UserService userService;

  @Operation(summary = "Создать нового пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь создан")
  @NoSecurityRequirements
  @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserResponseDto createUser(
      @RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
    log.debug("Принят запрос на создание пользователя {}", userCreateRequestDto);
    UserResponseDto response = userService.createUser(userCreateRequestDto);
    log.debug("Запрос на создание пользователя обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Получить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь найден")
  @GetMapping("/user/{id}")
  public UserResponseDto getUser(
      @Parameter(description = "id пользователя, по которому выполняется поиск")
      @PathVariable UUID id) {
    log.debug("Принят запрос на получение пользователя с id {}", id);
    UserResponseDto response = userService.getUser(id);
    log.debug("Запрос на получение пользователя обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Изменить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь изменен")
  @PutMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserResponseDto updateUser(
      @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto,
      @Parameter(description = "id пользователя, которого необходимо изменить")
      @PathVariable UUID id) {
    log.debug("Принят запрос на обновление пользователя {} с id {}", userUpdateRequestDto, id);
    UserResponseDto response = userService.updateUser(userUpdateRequestDto, id);
    log.debug("Запрос на обновление пользователя обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Удалить пользователя по id")
  @ApiResponse(responseCode = "200", description = "Пользователь удален")
  @DeleteMapping("/user/{id}")
  public UUID deleteUser(
      @Parameter(description = "id пользователя, которого необходимо удалить")
      @PathVariable UUID id) {
    log.debug("Принят запрос на удаление пользователя с id {}", id);
    UUID response = userService.deleteUser(id);
    log.debug("Запрос на удаление пользователя обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Назначить пользователю телеграм-логин", deprecated = true)
  @ApiResponse(responseCode = "200", description = "Пользователю назначен логин в telegram")
  @NoSecurityRequirements
  @PutMapping(value = "/telegram-login/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserResponseDto addTelegramLogin(@RequestBody UserUpdateRequestDto user,
                                          @PathVariable UUID id) {
    log.debug("Принят запрос на назначение логина в телеграм пользователю {} с id {}",
        user, id);
    UserResponseDto response = userService.addLogin(user, id);
    log.debug("Запрос на назначение логина в телеграм пользователю обработан успешно: {}",
        response);
    return response;
  }
}
