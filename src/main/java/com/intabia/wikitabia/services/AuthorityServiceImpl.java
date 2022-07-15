package com.intabia.wikitabia.services;

import com.intabia.wikitabia.repository.AuthoritiesDao;
import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.exceptions.DataAccessException;
import com.intabia.wikitabia.mappers.AuthoritiesMapper;
import com.intabia.wikitabia.services.service.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * реализация сервиса для работы с сущностями authority.
 */
@Service("authorityService")
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private static final String ROLE_NOT_FOUND_ERR_MSG = "Роль не найдена";

    private final AuthoritiesDao authoritiesDao;
    private final AuthoritiesMapper authoritiesMapper;

    @Override
    public AuthorityDto createAuthority(AuthorityDto authorityDto) {
        AuthorityEntity authority = authoritiesMapper.dtoToEntity(authorityDto);
        return authoritiesMapper.entityToDto(authoritiesDao.save(authority));
    }

    @Override
    public AuthorityDto updateAuthority(AuthorityDto authorityDto, UUID id) {
        AuthorityEntity authority = authoritiesDao.findById(id)
                .orElseThrow(() -> new DataAccessException(ROLE_NOT_FOUND_ERR_MSG));
        authoritiesMapper.updateEntity(authority, authorityDto);
        return authoritiesMapper.entityToDto(authoritiesDao.save(authority));
    }

    @Override
    public void deleteAuthority(UUID id) {
        authoritiesDao.deleteById(id);
    }

    @Override
    public AuthorityDto getAuthority(UUID id) {
        AuthorityEntity authority = authoritiesDao.findById(id)
                .orElseThrow(() -> new DataAccessException(ROLE_NOT_FOUND_ERR_MSG));
        return authoritiesMapper.entityToDto(authority);
    }
}
