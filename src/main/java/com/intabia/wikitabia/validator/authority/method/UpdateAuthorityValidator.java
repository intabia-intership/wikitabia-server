package com.intabia.wikitabia.validator.authority.method;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.ExistenceByIdValidator;
import com.intabia.wikitabia.validator.common.UniqueConstraintValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByIdInfo;
import com.intabia.wikitabia.validator.common.info.UniqueConstraintInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода
 * {@link com.intabia.wikitabia.service.AuthorityService#updateAuthority updateAuthority}.
 */
@Component
@RequiredArgsConstructor
public class UpdateAuthorityValidator {
  private static final String FRIENDLY_ROLE_NAME = "Название роли";
  private final AuthorityDao authorityDao;

  public void validate(AuthorityRequestDto authorityRequestDto, UUID id) {
    ExistenceByIdInfo<AuthorityEntity> existenceByIdInfo =
        ExistenceByIdInfo.<AuthorityEntity>exIdBuilder()
            .findFunc(authorityDao::findById)
            .exception(new EntityNotFoundException(
                AuthorityEntity.class, id
            ))
            .build();
    UniqueConstraintInfo<String, AuthorityEntity> uniqueConstraintInfo =
        UniqueConstraintInfo.<String, AuthorityEntity>builder()
            .findFunc(authorityDao::findAuthorityEntityByName)
            .exception(new UniqueFieldException(
                AuthorityRequestDto.class, FRIENDLY_ROLE_NAME, authorityRequestDto.getName()
            ))
            .build();

    ChainValidator.create()
        .add(new ExistenceByIdValidator<>(id, existenceByIdInfo))
        .add(new UniqueConstraintValidator<>(authorityRequestDto.getName(), uniqueConstraintInfo))
        .validate();
  }
}
