package com.intabia.wikitabia.service;

import com.intabia.wikitabia.dto.AuthorityDto;

import java.util.UUID;

/**
 * интерфес слоя сервисов для работы с authority.
 */
public interface AuthorityService {
    /**
     * создание новой authority.
     *
     * @param authorityDto - создаваемый authority
     * @return возвращает созданный authority
     */
    AuthorityDto createAuthority(AuthorityDto authorityDto);

    /**
     * модификация authority по id.
     *
     * @param authorityDto - новый authority
     * @param id - authority id
     * @return возвращает измененный authority
     */
    AuthorityDto updateAuthority(AuthorityDto authorityDto, UUID id);

    /**
     * удаление authority по id.
     *
     * @param id - authority id
     */
    void deleteAuthority(UUID id);

    /**
     * нахождение authority по id.
     *
     * @param id - authority id
     * @return возвращает найденный authority
     */
    AuthorityDto getAuthority(UUID id);
}
