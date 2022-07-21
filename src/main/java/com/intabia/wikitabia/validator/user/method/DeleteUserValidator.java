package com.intabia.wikitabia.validator.user.method;

import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.ExistenceByIdValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByIdInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода {@link com.intabia.wikitabia.service.UserService#deleteUser deleteUser}.
 */
@Component
@RequiredArgsConstructor
public class DeleteUserValidator {
  private final UserDao userDao;

  public void validate(UUID id) {
    ExistenceByIdInfo<UserEntity> existenceByIdInfo =
        ExistenceByIdInfo.<UserEntity>exIdBuilder()
            .findFunc(userDao::findById)
            .exception(new EntityNotFoundException(
                UserEntity.class, id
            ))
            .build();
    ChainValidator.create()
        .add(new ExistenceByIdValidator<>(id, existenceByIdInfo))
        .validate();
  }
}
