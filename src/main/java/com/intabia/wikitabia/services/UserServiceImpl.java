package com.intabia.wikitabia.services;

import com.intabia.wikitabia.exceptions.DataAccessException;
import com.intabia.wikitabia.mappers.UsersMapper;

import java.util.UUID;

import com.intabia.wikitabia.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.intabia.wikitabia.dao.UsersDao;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.entities.UserEntity;
import com.intabia.wikitabia.services.interfaces.UserService;

/**
 * реализация сервиса для работы с сущностями user.
 */
@Service("usersService")
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private final UsersMapper usersMapper;
  private final UsersDao usersDao;
  private final HashService hashService;

  @Override
  public UserDto createUser(UserDto userDto) {
    UserEntity userEntity = usersMapper.dtoToEntity(userDto);
    String hashedPassword = hashService.hashPassword(userEntity.getPassword());
    userEntity.setPassword(hashedPassword);
    return usersMapper.entityToDto(usersDao.save(userEntity));
  }

  @Override
  public UserDto updateUser(UserDto userDto) {
    UserEntity userEntity = usersMapper.dtoToEntity(userDto);
    String password = userEntity.getPassword();
    String hashPassword = hashService.hashPassword(password);
    userEntity.setPassword(hashPassword);
    return usersMapper.entityToDto(usersDao.save(userEntity));
  }

  @Override
  public void deleteUser(UUID id) {
    usersDao.deleteById(id);
  }

  /**
   * метод для обращения из frontend.
   *
   * @param id принимает id user
   * @return возвращает UserDto
   */
  @Override
  public UserDto getUser(UUID id) {
    UserEntity userEntity = usersDao.findById(id)
        .orElseThrow(() -> new DataAccessException("Пользователь не найден"));
    ;
    return usersMapper.entityToDto(userEntity);
  }

  /**
   * метод для авторизации. обращение из frontend.
   *
   * @param userLogin принимает логин пользователя
   * @param password  принимает пароль пользователя и хеширует его
   * @return возвращает boolean нашлось ли совпадение в БД
   */
  @Override
  public UserDto authorisation(String userLogin, String password) {
    String hashedPassword = hashService.hashPassword(password);
    UserEntity user = usersDao.findUsersEntityByLoginAndPassword(userLogin, hashedPassword)
        .orElseThrow(() -> new CustomException("Ошибка авторизации"));
    return usersMapper.entityToDto(user);
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
    return usersMapper.entityToDto(usersDao.findUserEntityByLogin(login)
        .orElseThrow(() -> new DataAccessException("Пользователь не найден")));
  }
}
