package com.intabia.wikitabia.services.interfaces;

import com.intabia.wikitabia.dto.AuthorityDto;

import java.util.UUID;

public interface AuthorityService {
    AuthorityDto createAuthority(AuthorityDto authorityDto);

    AuthorityDto updateAuthority(AuthorityDto authorityDto);

    void deleteAuthority(UUID id);

    AuthorityDto getAuthority(UUID id);
}
