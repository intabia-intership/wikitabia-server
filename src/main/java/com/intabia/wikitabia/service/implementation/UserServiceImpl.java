package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.mappers.UsersMapper;
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
@Service("usersService")
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private final UsersMapper usersMapper;
  private final UsersDao usersDao;
  private final AuthoritiesDao authoritiesDao;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(CreateUserDto createUserDto) {
    Set<AuthorityEntity> authorities = new HashSet<>(createUserDto.getAuthorities().size());
    for (String authorityName : createUserDto.getAuthorities()) {
      AuthorityEntity authority = authoritiesDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> InvalidBodyException.create(new String[]{"authorities"}, authorityName,
              "Роль добавляемого пользователя должна существовать"));
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
    UserEntity user = usersDao.findById(id)
        .orElseThrow(() -> DataNotFoundException.create(UserEntity.class, id));

    Set<AuthorityEntity> authorities = new HashSet<>(updateUserDto.getAuthorities().size());
    for (String authorityName : updateUserDto.getAuthorities()) {
      AuthorityEntity authority = authoritiesDao.findAuthorityEntityByName(authorityName)
          .orElseThrow(() -> InvalidBodyException.create(new String[]{"authorities"}, authorityName,
              "Роль добавляемого пользователя должна существовать"));
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
        .orElseThrow(() -> DataNotFoundException.create(UserEntity.class, id));
    return usersMapper.entityToDto(userEntity);
  }

  @Override
  public void addLogin(UserDto user) {
    UserEntity userEntity = usersDao.findById(user.getId())
        .orElseThrow(() -> DataNotFoundException.create(UserEntity.class, user.getId()));
    userEntity.setTelegramLogin(user.getTelegramLogin());
    usersDao.save(userEntity);
  }
}
