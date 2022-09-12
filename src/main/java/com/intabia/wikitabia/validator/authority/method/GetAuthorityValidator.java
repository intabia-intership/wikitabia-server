package com.intabia.wikitabia.validator.authority.method;

import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.ExistenceByIdValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByIdInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода
 * {@link com.intabia.wikitabia.service.AuthorityService#getAuthority getAuthority}.
 */
@Component
@RequiredArgsConstructor
public class GetAuthorityValidator {
  private final AuthorityDao authorityDao;

  public void validate(UUID id) {
    ExistenceByIdInfo<AuthorityEntity> info =
        ExistenceByIdInfo.<AuthorityEntity>exIdBuilder()
            .findFunc(authorityDao::findById)
            .exception(new EntityNotFoundException(
                AuthorityEntity.class, id
            ))
            .build();
    ChainValidator.create()
        .add(new ExistenceByIdValidator<>(id, info))
        .validate();
  }
}
