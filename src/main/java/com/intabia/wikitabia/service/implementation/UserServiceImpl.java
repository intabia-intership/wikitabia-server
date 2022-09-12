package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.exception.UnexpectedException;
import com.intabia.wikitabia.mappers.entity.UsersMapper;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.service.UserService;
import com.intabia.wikitabia.validator.user.UserValidator;
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
  private final UserValidator userValidator;
  private final UsersMapper usersMapper;
  private final UserDao userDao;
  private final AuthorityDao authorityDao;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
    userValidator.createUserValidate(userCreateRequestDto);

    Set<AuthorityEntity> authorities = new HashSet<>(userCreateRequestDto.getAuthorities().size());
    for (String authorityName : userCreateRequestDto.getAuthorities()) {
      authorities.add(authorityDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(UnexpectedException::new));
    }
    UserEntity userEntity = usersMapper.dtoToEntity(userCreateRequestDto);
    userEntity.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(encodedPassword);
    return usersMapper.entityToDto(userDao.save(userEntity));
  }

  @Override
  public UserResponseDto getUser(UUID id) {
    userValidator.getUserValidate(id);

    UserEntity userEntity = userDao.findById(id)
        .orElseThrow(UnexpectedException::new);
    return usersMapper.entityToDto(userEntity);
  }

  @Override
  public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto, UUID id) {
    userValidator.updateUserValidate(userUpdateRequestDto, id);

    UserEntity user = userDao.findById(id)
        .orElseThrow(UnexpectedException::new);
    Set<AuthorityEntity> authorities = new HashSet<>(userUpdateRequestDto.getAuthorities().size());
    for (String authorityName : userUpdateRequestDto.getAuthorities()) {
      authorities.add(authorityDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(UnexpectedException::new));
    }
    usersMapper.updateEntity(user, userUpdateRequestDto);
    user.setAuthorities(authorities);
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    return usersMapper.entityToDto(userDao.save(user));
  }

  @Override
  public UUID deleteUser(UUID id) {
    userValidator.deleteUserValidate(id);

    userDao.deleteById(id);
    return id;
  }

  @Override
  public UserResponseDto addLogin(UserUpdateRequestDto userUpdateRequestDto, UUID id) {
    userValidator.addLoginValidate(userUpdateRequestDto, id);

    UserEntity user = userDao.findById(id)
        .orElseThrow(UnexpectedException::new);
    user.setTelegramLogin(user.getTelegramLogin());
    return usersMapper.entityToDto(userDao.save(user));
  }
}
