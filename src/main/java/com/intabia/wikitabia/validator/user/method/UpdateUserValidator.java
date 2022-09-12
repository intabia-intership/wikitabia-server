package com.intabia.wikitabia.validator.user.method;

import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.validator.ChainValidator;
import com.intabia.wikitabia.validator.common.ExistenceByIdValidator;
import com.intabia.wikitabia.validator.common.ExistenceByUniqueFieldValidator;
import com.intabia.wikitabia.validator.common.UniqueConstraintValidator;
import com.intabia.wikitabia.validator.common.info.ExistenceByIdInfo;
import com.intabia.wikitabia.validator.common.info.ExistenceByUniqueFieldInfo;
import com.intabia.wikitabia.validator.common.info.UniqueConstraintInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для метода {@link com.intabia.wikitabia.service.UserService#updateUser updateUser}.
 */
@Component
@RequiredArgsConstructor
public class UpdateUserValidator {
  private static final String FRIENDLY_LOGIN_NAME = "Логин пользователя";
  private static final String FRIENDLY_TELEGRAM_LOGIN_NAME = "Логин пользователя в telegram";
  private static final String FRIENDLY_ROLE_NAME = "Роль добавляемого пользователя";
  private final UserDao userDao;
  private final AuthorityDao authorityDao;

  public void validate(UserUpdateRequestDto requestDto, UUID id) {
    UniqueConstraintInfo<String, UserEntity> uniqueLoginInfo =
        UniqueConstraintInfo.<String, UserEntity>builder()
            .findFunc(userDao::findUserEntityByLogin)
            .exception(new UniqueFieldException(
                UserUpdateRequestDto.class, FRIENDLY_LOGIN_NAME, requestDto.getLogin()
            ))
            .id(id)
            .build();

    UniqueConstraintInfo<String, UserEntity> uniqueTelegramLoginInfo =
        UniqueConstraintInfo.<String, UserEntity>builder()
            .findFunc(userDao::findUserEntityByTelegramLogin)
            .exception(new InvalidBodyException(
                UserUpdateRequestDto.class, FRIENDLY_TELEGRAM_LOGIN_NAME,
                requestDto.getTelegramLogin()
            ))
            .id(id)
            .build();

    ExistenceByIdInfo<UserEntity> existenceByIdInfo = ExistenceByIdInfo.<UserEntity>exIdBuilder()
        .findFunc(userDao::findById)
        .exception(new EntityNotFoundException(UserEntity.class, id))
        .build();

    ChainValidator chainValidator = ChainValidator.create()
        .add(new UniqueConstraintValidator<>(requestDto.getLogin(), uniqueLoginInfo))
        .add(
            new UniqueConstraintValidator<>(requestDto.getTelegramLogin(), uniqueTelegramLoginInfo))
        .add(new ExistenceByIdValidator<>(id, existenceByIdInfo));

    for (String authorityName : requestDto.getAuthorities()) {
      ExistenceByUniqueFieldInfo<String, AuthorityEntity> info =
          ExistenceByUniqueFieldInfo.<String, AuthorityEntity>exBuilder()
              .findFunc(authorityDao::findAuthorityEntityByName)
              .exception(new InvalidBodyException(
                  UserUpdateRequestDto.class, FRIENDLY_ROLE_NAME, authorityName
              ))
              .build();
      chainValidator.add(new ExistenceByUniqueFieldValidator<>(authorityName, info));
    }
    chainValidator.validate();
  }
}
