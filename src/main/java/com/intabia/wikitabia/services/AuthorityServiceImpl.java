package com.intabia.wikitabia.services;

import com.intabia.wikitabia.dao.AuthoritiesDao;
import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.entities.AuthorityEntity;
import com.intabia.wikitabia.exceptions.DataAccessException;
import com.intabia.wikitabia.mappers.AuthoritiesMapper;
import com.intabia.wikitabia.services.interfaces.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("authorityService")
@AllArgsConstructor
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthoritiesDao authoritiesDao;
    private final AuthoritiesMapper authoritiesMapper;

    @Override
    public AuthorityDto createAuthority(AuthorityDto authorityDto) {
        AuthorityEntity authority = authoritiesMapper.dtoToEntity(authorityDto);
        return authoritiesMapper.entityToDto(authoritiesDao.save(authority));
    }

    @Override
    public AuthorityDto updateAuthority(AuthorityDto authorityDto) {
        return createAuthority(authorityDto);
    }

    @Override
    public void deleteAuthority(UUID id) {
        authoritiesDao.deleteById(id);
    }

    @Override
    public AuthorityDto getAuthority(UUID id) {
        AuthorityEntity authority = authoritiesDao.findById(id)
                .orElseThrow(() -> new DataAccessException("Роль не найдена"));
        return authoritiesMapper.entityToDto(authority);
    }
}
