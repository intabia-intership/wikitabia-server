package com.intabia.wikitabia.validator.user.method;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.exception.EntityMustExistException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.ExistenceByUniqueFieldValidator;
import com.intabia.wikitabia.validator.common.UniqueConstraintValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByUniqueFieldInfo;
import com.intabia.wikitabia.validator.common.info.UniqueConstraintInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода {@link com.intabia.wikitabia.service.UserService#createUser createUser}.
 */
@Component
@RequiredArgsConstructor
public class CreateUserValidator {
  private static final String FRIENDLY_LOGIN_NAME = "Логин пользователя";
  private static final String FRIENDLY_ROLE_NAME = "Роль добавляемого пользователя";
  private final UserDao userDao;
  private final AuthorityDao authorityDao;

  public void validate(UserCreateRequestDto requestDto) {
    ChainValidator chainValidator = ChainValidator.create();
    UniqueConstraintInfo<String, UserEntity> uniqueInfo =
        UniqueConstraintInfo.<String, UserEntity>builder()
            .findFunc(userDao::findUserEntityByLogin)
            .exception(new UniqueFieldException(
                UserCreateRequestDto.class, FRIENDLY_LOGIN_NAME, requestDto.getLogin()))
            .build();
    chainValidator.add(new UniqueConstraintValidator<>(requestDto.getLogin(), uniqueInfo));

    for (String authorityName : requestDto.getAuthorities()) {
      ExistenceByUniqueFieldInfo<String, AuthorityEntity> info =
          ExistenceByUniqueFieldInfo.<String, AuthorityEntity>exBuilder()
              .findFunc(authorityDao::findAuthorityEntityByName)
              .exception(new EntityMustExistException(
                  UserCreateRequestDto.class, FRIENDLY_ROLE_NAME, authorityName
              ))
              .build();
      chainValidator.add(new ExistenceByUniqueFieldValidator<>(authorityName, info));
    }
    chainValidator.validate();
  }
}
