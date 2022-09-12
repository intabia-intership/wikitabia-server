package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.controller.annotation.RequireAdmin;
import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.service.AuthorityService;
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
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
@Tag(name = "Роль API", description = "API для операций над ролями")
@Slf4j(topic = "com.intabia.wikitabia.logger")
public class AuthorityRestController {
  private final AuthorityService authorityService;

  @Operation(summary = "Создать новую роль")
  @ApiResponse(responseCode = "200", description = "Роль создана")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @RequireAdmin
  public AuthorityResponseDto createAuthority(
      @RequestBody @Valid AuthorityRequestDto authorityRequestDto) {
    log.debug("Принят запрос на создание роли {}", authorityRequestDto);
    AuthorityResponseDto response = authorityService.createAuthority(authorityRequestDto);
    log.debug("Запрос на создание роли обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Найти роль по id")
  @ApiResponse(responseCode = "200", description = "Роль найдена")
  @GetMapping("/{id}")
  @RequireAdmin
  public AuthorityResponseDto getAuthority(
      @Parameter(description = "id роли, по которому выполняется поиск")
      @PathVariable UUID id) {
    log.debug("Принят запрос на получение роли с id {}", id);
    AuthorityResponseDto response = authorityService.getAuthority(id);
    log.debug("Запрос на получение роли обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Изменить роль по id")
  @ApiResponse(responseCode = "200", description = "Роль изменена")
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @RequireAdmin
  public AuthorityResponseDto updateAuthority(
      @RequestBody @Valid AuthorityRequestDto authorityRequestDto,
      @Parameter(description = "id роли, которую необходимо изменить")
      @PathVariable UUID id) {
    log.debug("Принят запрос на обновление роли {} с id {}", authorityRequestDto, id);
    AuthorityResponseDto response = authorityService.updateAuthority(authorityRequestDto, id);
    log.debug("Запрос на обновление роли обработан успешно: {}", response);
    return response;
  }

  @Operation(summary = "Удалить роль по id")
  @ApiResponse(responseCode = "200", description = "Роль удалена")
  @DeleteMapping("/{id}")
  @RequireAdmin
  public UUID deleteAuthority(
      @Parameter(description = "id роли, которую необходимо удалить")
      @PathVariable UUID id) {
    log.debug("Принят запрос на удаление роли с id {}", id);
    UUID response = authorityService.deleteAuthority(id);
    log.debug("Запрос на удаление роли обработан успешно: {}", response);
    return response;
  }
}
