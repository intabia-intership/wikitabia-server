package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.service.AuthorityService;
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
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
@Tag(name = "Роль API", description = "API для операций над ролями")
public class AuthorityRestController {
  private final AuthorityService authorityService;

  @Operation(summary = "Создать новую роль")
  @ApiResponse(responseCode = "200", description = "Роль создана")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthorityDto saveAuthority(
      @RequestBody @Valid AuthorityDto authorityDto) {
    return authorityService.createAuthority(authorityDto);
  }

  @Operation(summary = "Найти роль по id")
  @ApiResponse(responseCode = "200", description = "Роль найдена")
  @GetMapping("/{id}")
  public AuthorityDto getAuthority(
      @Parameter(description = "id роли, по которому выполняется поиск")
      @PathVariable UUID id) {
    return authorityService.getAuthority(id);
  }

  @Operation(summary = "Изменить роль по id")
  @ApiResponse(responseCode = "200", description = "Роль изменена")
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthorityDto updateAuthority(
      @RequestBody @Valid AuthorityDto authorityDto,
      @Parameter(description = "id роли, которую необходимо изменить")
      @PathVariable UUID id) {
    return authorityService.updateAuthority(authorityDto, id);
  }

  @Operation(summary = "Удалить роль по id")
  @ApiResponse(responseCode = "200", description = "Роль удалена")
  @DeleteMapping("/{id}")
  public UUID deleteAuthority(
      @Parameter(description = "id роли, которую необходимо удалить")
      @PathVariable UUID id) {
    authorityService.deleteAuthority(id);
    return id;
  }
}
