package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.exception.EntityMustExistException;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.UniqueFieldException;
import com.intabia.wikitabia.mappers.entity.UsersMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthoritiesDao;
import com.intabia.wikitabia.repository.UsersDao;
import com.intabia.wikitabia.service.UserService;
import java.util.HashSet;
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
  private final UsersDao usersDao;
  private final AuthoritiesDao authoritiesDao;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(CreateUserDto createUserDto) {
    if (usersDao.existsByLogin(createUserDto.getLogin())) {
      throw new UniqueFieldException(CreateUserDto.class, FRIENDLY_LOGIN_NAME,
          createUserDto.getLogin());
    }
    Set<AuthorityEntity> authorities = new HashSet<>(createUserDto.getAuthorities().size());
    for (String authorityName : createUserDto.getAuthorities()) {
      AuthorityEntity authority = authoritiesDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> new EntityMustExistException(CreateUserDto.class, FRIENDLY_ROLE_NAME,
              authorityName));
      authorities.add(authority);
    }

    UserEntity userEntity = usersMapper.dtoToEntity(createUserDto);
    userEntity.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(encodedPassword);
    return usersMapper.entityToDto(usersDao.save(userEntity));
  }

  @Override
  public UserDto updateUser(UpdateUserDto updateUserDto, UUID id) {
    UserEntity sameUser = usersDao.findUserEntityByLogin(updateUserDto.getLogin())
        .orElse(null);
    if (sameUser != null && !sameUser.getId().equals(id)) {
      throw new UniqueFieldException(UpdateUserDto.class, FRIENDLY_LOGIN_NAME,
          updateUserDto.getLogin());
    }

    sameUser = usersDao.findUserEntityByTelegramLogin(updateUserDto.getTelegramLogin())
        .orElse(null);
    if (sameUser != null && !sameUser.getId().equals(id)) {
      throw new UniqueFieldException(UpdateUserDto.class, FRIENDLY_TELEGRAM_LOGIN_NAME,
          updateUserDto.getTelegramLogin());
    }

    UserEntity user = usersDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    Set<AuthorityEntity> authorities = new HashSet<>(updateUserDto.getAuthorities().size());
    for (String authorityName : updateUserDto.getAuthorities()) {
      AuthorityEntity authority = authoritiesDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> new EntityMustExistException(UpdateUserDto.class, FRIENDLY_ROLE_NAME,
              authorityName));
      authorities.add(authority);
    }
    usersMapper.updateEntity(user, updateUserDto);
    user.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    return usersMapper.entityToDto(usersDao.save(user));
  }

  @Override
  public void deleteUser(UUID id) {
    usersDao.deleteById(id);
  }

  @Override
  public UserDto getUser(UUID id) {
    UserEntity userEntity = usersDao.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    return usersMapper.entityToDto(userEntity);
  }

  @Override
  public void addLogin(UserDto user) {
    UserEntity userEntity = usersDao.findById(user.getId())
        .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, user.getId()));
    userEntity.setTelegramLogin(user.getTelegramLogin());
    usersDao.save(userEntity);
  }
}
