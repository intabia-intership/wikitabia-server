package com.intabia.wikitabia.service;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import java.util.UUID;

/**
 * интерфес слоя сервисов для работы с authority.
 */
public interface AuthorityService {
  /**
   * создание новой authority.
   *
   * @param authorityRequestDto - создаваемый authority
   * @return возвращает созданный authority
   */
  AuthorityResponseDto createAuthority(AuthorityRequestDto authorityRequestDto);

  /**
   * модификация authority по id.
   *
   * @param authorityRequestDto - новый authority
   * @param id           - authority id
   * @return возвращает измененный authority
   */
  AuthorityResponseDto updateAuthority(AuthorityRequestDto authorityRequestDto, UUID id);

  /**
   * удаление authority по id.
   *
   * @param id - authority id
   */
  UUID deleteAuthority(UUID id);

  /**
   * нахождение authority по id.
   *
   * @param id - authority id
   * @return возвращает найденный authority
   */
  AuthorityResponseDto getAuthority(UUID id);
}
