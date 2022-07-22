package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.services.service.AuthorityService;
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

/**
 * REST контроллер для операций над authority.
 */
@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
@SecurityRequirement(name = "wikitabia basic")
@SecurityRequirement(name = "wikitabia keycloak")
public class AuthorityRestController {
  private final AuthorityService authorityService;

  /**
   * создание новой authority.
   *
   * @param authorityDto - создаваемый authority
   * @return возвращает созданный authority
   */
  @Operation(summary = "Создать новую роль")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthorityDto saveAuthority(@RequestBody @Valid AuthorityDto authorityDto) {
    return authorityService.createAuthority(authorityDto);
  }

  /**
   * нахождение authority по id.
   *
   * @param id - authority id
   * @return возвращает найденный authority
   */
  @Operation(summary = "Найти роль по id")
  @GetMapping("/{id}")
  public AuthorityDto getAuthority(@PathVariable UUID id) {
    return authorityService.getAuthority(id);
  }

  /**
   * модификация authority по id.
   *
   * @param authorityDto - новый authority
   * @param id           - authority id
   * @return возвращает измененный authority
   */
  @Operation(summary = "Изменить роль по id")
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthorityDto updateAuthority(@RequestBody @Valid AuthorityDto authorityDto,
                                      @PathVariable UUID id) {
    return authorityService.updateAuthority(authorityDto, id);
  }

  /**
   * удаление authority по id.
   *
   * @param id - authority id
   * @return возвращает authority id
   */
  @Operation(summary = "Удалить роль по id")
  @DeleteMapping("/{id}")
  public UUID deleteAuthority(@PathVariable UUID id) {
    authorityService.deleteAuthority(id);
    return id;
  }
}
