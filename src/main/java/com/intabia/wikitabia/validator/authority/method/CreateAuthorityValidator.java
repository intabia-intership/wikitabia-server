package com.intabia.wikitabia.validator.authority.method;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.UniqueConstraintValidator;
import com.intabia.wikitabia.validator.common.info.UniqueConstraintInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода
 * {@link com.intabia.wikitabia.service.AuthorityService#createAuthority createAuthority}.
 */
@Component
@RequiredArgsConstructor
public class CreateAuthorityValidator {
  private static final String FRIENDLY_ROLE_NAME = "Название роли";
  private final AuthorityDao authorityDao;

  public void validate(AuthorityRequestDto authorityRequestDto) {
    UniqueConstraintInfo<String, AuthorityEntity> info =
        UniqueConstraintInfo.<String, AuthorityEntity>builder()
            .findFunc(authorityDao::findAuthorityEntityByName)
            .exception(new UniqueFieldException(
                AuthorityRequestDto.class, FRIENDLY_ROLE_NAME, authorityRequestDto.getName()
            ))
            .build();
    ChainValidator.create()
        .add(new UniqueConstraintValidator<>(authorityRequestDto.getName(), info))
        .validate();
  }
}
