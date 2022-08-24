package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.mappers.entity.AuthoritiesMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
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

  private final AuthorityDao authorityDao;
  private final AuthoritiesMapper authoritiesMapper;

  @Override
  public AuthorityResponseDto createAuthority(AuthorityRequestDto authorityDto) {
    if (authorityDao.existsByName(authorityDto.getName())) {
      throw new UniqueFieldException(AuthorityRequestDto.class, FRIENDLY_ROLE_NAME,
          authorityDto.getName());
    }
    AuthorityEntity authority = authoritiesMapper.dtoToEntity(authorityDto);
    return authoritiesMapper.entityToDto(authorityDao.save(authority));
  }

  @Override
  public AuthorityResponseDto updateAuthority(AuthorityRequestDto authorityDto, UUID id) {
    AuthorityEntity authority = authorityDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(AuthorityEntity.class, id));

    AuthorityEntity sameAuthority = authorityDao.findAuthorityEntityByName(authorityDto.getName())
        .orElse(null);
    if (sameAuthority != null && !sameAuthority.getId().equals(id)) {
      throw new UniqueFieldException(AuthorityRequestDto.class, FRIENDLY_ROLE_NAME,
          authorityDto.getName());
    }

    authoritiesMapper.updateEntity(authority, authorityDto);
    return authoritiesMapper.entityToDto(authorityDao.save(authority));
  }

  @Override
  public UUID deleteAuthority(UUID id) {
    if (!authorityDao.existsById(id)) {
      throw new EntityNotFoundException(AuthorityEntity.class, id);
    }

    authorityDao.deleteById(id);
    return id;
  }

  @Override
  public AuthorityResponseDto getAuthority(UUID id) {
    AuthorityEntity authority = authorityDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(AuthorityEntity.class, id));

    return authoritiesMapper.entityToDto(authority);
  }
}
