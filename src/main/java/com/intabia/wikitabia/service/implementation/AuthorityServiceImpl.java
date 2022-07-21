package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.mappers.entity.AuthoritiesMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.service.AuthorityService;
import com.intabia.wikitabia.validator.authority.AuthorityValidator;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * реализация сервиса для работы с сущностями authority.
 */
@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
  private final AuthorityValidator authorityValidator;
  private final AuthorityDao authorityDao;
  private final AuthoritiesMapper authoritiesMapper;

  @Override
  public AuthorityResponseDto createAuthority(AuthorityRequestDto authorityDto) {
    authorityValidator.createAuthorityValidate(authorityDto);

    AuthorityEntity authority = authoritiesMapper.dtoToEntity(authorityDto);
    return authoritiesMapper.entityToDto(authorityDao.save(authority));
  }

  @Override
  public AuthorityResponseDto getAuthority(UUID id) {
    authorityValidator.getAuthorityValidate(id);

    AuthorityEntity authority = authorityDao.findById(id).get();
    return authoritiesMapper.entityToDto(authority);
  }

  @Override
  public AuthorityResponseDto updateAuthority(AuthorityRequestDto authorityDto, UUID id) {
    authorityValidator.updateAuthorityValidate(authorityDto, id);

    AuthorityEntity authority = authorityDao.findById(id).get();
    authoritiesMapper.updateEntity(authority, authorityDto);
    return authoritiesMapper.entityToDto(authorityDao.save(authority));
  }

  @Override
  public UUID deleteAuthority(UUID id) {
    authorityValidator.deleteAuthorityValidate(id);

    authorityDao.deleteById(id);
    return id;
  }
}
