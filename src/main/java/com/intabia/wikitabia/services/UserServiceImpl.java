package com.intabia.wikitabia.services;

import com.intabia.wikitabia.exceptions.DataAccessException;
import com.intabia.wikitabia.mappers.UsersMapper;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.intabia.wikitabia.dao.UsersDao;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.entities.UserEntity;

/**
 * реализация сервиса для работы с сущностями user.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UsersMapper usersMapper;
  private final UsersDao usersDao;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDto saveUser(UserDto userDto) {
    UserEntity userEntity = usersMapper.dtoToEntity(userDto);
    userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userDto = usersMapper.entityToDto(usersDao.save(userEntity));
    userDto.setPassword(null);
    return userDto;
  }

  @Override
  public UserDto updateUser(UserDto userDto) {
    return saveUser(userDto);
  }

  @Override
  public void deleteUser(UUID id) {
    usersDao.deleteById(id);
  }

  @Override
  public UserDto getUser(UUID id) {
    UserEntity userEntity = usersDao.findById(id)
        .orElseThrow(() -> new DataAccessException("Пользователь не найден"));
    ;
    return usersMapper.entityToDto(userEntity);
  }

  @Override
  public void addLogin(UserDto user) {
    UserEntity userEntity = usersDao.findById(user.getId()).orElse(null);
    if (userEntity == null) {
      return;
    }
    userEntity.setTelegramLogin(user.getTelegramLogin());
    usersDao.save(userEntity);
  }

  @Override
  public UserDto findUserByLogin(String login) {
    return usersMapper.entityToDto(usersDao.findUserEntityByUsername(login)
        .orElseThrow(() -> new DataAccessException("Пользователь не найден")));
  }
}
