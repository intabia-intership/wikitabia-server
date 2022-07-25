package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.repository.AuthoritiesDao;
import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.mappers.AuthoritiesMapper;
import com.intabia.wikitabia.service.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * реализация сервиса для работы с сущностями authority.
 */
@Service("authorityService")
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
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
        .orElseThrow(
            () -> DataNotFoundException.create(AuthorityEntity.class, id));
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
        .orElseThrow(
            () -> DataNotFoundException.create(AuthorityEntity.class, id));
    return authoritiesMapper.entityToDto(authority);
  }
}
