package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.AuthorityDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.mappers.entity.AuthoritiesMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthoritiesDao;
import com.intabia.wikitabia.service.AuthorityService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * реализация сервиса для работы с сущностями authority.
 */
@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
  private static final String FRIENDLY_ROLE_NAME = "Название роли";

  private final AuthoritiesDao authoritiesDao;
  private final AuthoritiesMapper authoritiesMapper;

  @Override
  public AuthorityDto createAuthority(AuthorityDto authorityDto) {
    if (authoritiesDao.existsByName(authorityDto.getName())) {
      throw new UniqueFieldException(AuthorityDto.class, FRIENDLY_ROLE_NAME,
          authorityDto.getName());
    }
    AuthorityEntity authority = authoritiesMapper.dtoToEntity(authorityDto);
    return authoritiesMapper.entityToDto(authoritiesDao.save(authority));
  }

  @Override
  public AuthorityDto updateAuthority(AuthorityDto authorityDto, UUID id) {
    AuthorityEntity authority = authoritiesDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(AuthorityEntity.class, id));

    AuthorityEntity sameAuthority = authoritiesDao.findAuthorityEntityByName(authorityDto.getName())
        .orElse(null);
    if (sameAuthority != null && !sameAuthority.getId().equals(id)) {
      throw new UniqueFieldException(AuthorityDto.class, FRIENDLY_ROLE_NAME,
          authorityDto.getName());
    }

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
        .orElseThrow(() -> new EntityNotFoundException(AuthorityEntity.class, id));
    return authoritiesMapper.entityToDto(authority);
  }
}
