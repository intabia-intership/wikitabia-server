package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.dto.util.FieldRulesConstant;
import com.intabia.wikitabia.exception.EntityMustExistException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.mappers.UsersMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * реализация сервиса для работы с сущностями user.
 */
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private static final String FRIENDLY_LOGIN_NAME =
      "Логин пользователя";
  private static final String FRIENDLY_TELEGRAM_LOGIN_NAME =
      "Логин пользователя в telegram";
  private static final String FRIENDLY_ROLE_NAME =
      "Роль добавляемого пользователя";

  private final UsersMapper usersMapper;
  private final UserDao userDao;
  private final AuthorityDao authorityDao;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
    if (userDao.existsByLogin(userCreateRequestDto.getLogin())) {
      throw new UniqueFieldException(CreateUserDto.class, FRIENDLY_LOGIN_NAME,
          createUserDto.getLogin());
    }

    Set<AuthorityEntity> authorities = new HashSet<>(userCreateRequestDto.getAuthorities().size());
    for (String authorityName : userCreateRequestDto.getAuthorities()) {
      AuthorityEntity authority = authorityDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> new EntityMustExistException(CreateUserDto.class, FRIENDLY_ROLE_NAME,
              authorityName));
      authorities.add(authority);
    }
    UserEntity userEntity = usersMapper.dtoToEntity(userCreateRequestDto);
    userEntity.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(encodedPassword);
    return usersMapper.entityToDto(userDao.save(userEntity));
  }

  @Override
  public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto, UUID id) {
    UserEntity sameUser = userDao.findUserEntityByLogin(userUpdateRequestDto.getLogin())
        .orElse(null);
    if (sameUser != null && !sameUser.getId().equals(id)) {
      throw new UniqueFieldException(UpdateUserDto.class, FRIENDLY_LOGIN_NAME,
          updateUserDto.getLogin());
    }

    sameUser = userDao.findUserEntityByTelegramLogin(userUpdateRequestDto.getTelegramLogin())
        .orElse(null);
    if (sameUser != null && !sameUser.getId().equals(id)) {
      throw new UniqueFieldException(UpdateUserDto.class, FRIENDLY_TELEGRAM_LOGIN_NAME,
          updateUserDto.getTelegramLogin());
    }

    UserEntity user = userDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));

    Set<AuthorityEntity> authorities = new HashSet<>(userUpdateRequestDto.getAuthorities().size());
    for (String authorityName : userUpdateRequestDto.getAuthorities()) {
      AuthorityEntity authority = authorityDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> new EntityMustExistException(UpdateUserDto.class, FRIENDLY_ROLE_NAME,
              authorityName));
      authorities.add(authority);
    }
    usersMapper.updateEntity(user, userUpdateRequestDto);
    user.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    return usersMapper.entityToDto(userDao.save(user));
  }

  @Override
  public UUID deleteUser(UUID id) {
    if (!userDao.existsById(id)) {
      throw new EntityNotFoundException(UserEntity.class, id);
    }

    userDao.deleteById(id);
    return id;
  }

  @Override
  public UserResponseDto getUser(UUID id) {
    UserEntity userEntity = userDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    return usersMapper.entityToDto(userEntity);
  }

  @Override
  public UserResponseDto addLogin(UserUpdateRequestDto userUpdateRequestDto, UUID id) {
    UserEntity user = userDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, user.getId()));

    user.setTelegramLogin(user.getTelegramLogin());
    return usersMapper.entityToDto(userDao.save(user));
  }
}
