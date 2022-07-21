package com.intabia.wikitabia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.repository.UserDao;
import com.intabia.wikitabia.util.TestConstant;
import com.intabia.wikitabia.util.assertion.UserAssert;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest extends ServiceTestWithPostgresContainer {
  @Autowired
  private UserService userService;

  @Autowired
  private UserDao userDao;

  @Autowired
  private AuthorityDao authorityDao;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void createUser__whenEverythingFine__thenSaveUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(List.of(savedAuthority.getName()))
        .build();
    UserResponseDto savedUser = userService.createUser(userCreate);

    Optional<UserEntity> opt = userDao.findById(savedUser.getId());
    assertThat(opt).isPresent();
    UserEntity userInDb = opt.get();

    UserAssert.assertThat(userInDb).isEqualTo(userCreate, passwordEncoder);

    UserAssert.assertThat(userInDb).isEqualTo(savedUser);
  }

  @Test
  public void createUser__whenUserLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    userDao.save(user);
    userDao.flush();

    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Peter")
        .lastName("Peterov")
        .login(user.getLogin())
        .password("123456")
        .authorities(List.of(savedAuthority.getName()))
        .build();
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.createUser(userCreate));
  }

  @Test
  public void createUser__whenUserAuthorityNotExist__thenInvalidBodyException() {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(List.of("USER"))
        .build();

    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.createUser(userCreate));
  }

  @Test
  public void getUser__whenEverythingFine__thenGetUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    UserEntity savedUser = userDao.save(user);
    userDao.flush();

    UserResponseDto getUser = userService.getUser(savedUser.getId());

    UserAssert.assertThat(savedUser).isEqualTo(getUser);
  }

  @Test
  public void getUser__whenUserNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(DataNotFoundException.class).isThrownBy(() ->
        userService.getUser(TestConstant.TEST_UUID));
  }

  @Test
  public void updateUser__whenEverythingFine__thenUpdateUser() {
    AuthorityEntity savedAuthority1 = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    AuthorityEntity savedAuthority2 = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE.withName("ADMIN"));
    authorityDao.flush();

    UserEntity user = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority1))
        .build();
    UserEntity savedUser = userDao.save(user);
    userDao.flush();

    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Peter")
        .lastName("Peterov")
        .login("Pit")
        .telegramLogin("Pit_telega")
        .password("654321")
        .authorities(List.of(savedAuthority2.getName()))
        .build();
    UserResponseDto updatedUser = userService.updateUser(userUpdate, savedUser.getId());

    Optional<UserEntity> opt = userDao.findById(savedUser.getId());
    assertThat(opt).isPresent();
    UserEntity userInDb = opt.get();

    UserAssert.assertThat(userInDb).isEqualTo(userUpdate, passwordEncoder);

    UserAssert.assertThat(userInDb).isEqualTo(updatedUser);
  }

  @Test
  public void updateUser__whenUserLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user1 = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    UserEntity user2 = UserEntity.builder()
        .firstName("Peter")
        .lastName("Peterov")
        .login("Pit")
        .telegramLogin("Pit_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    userDao.save(user1);
    UserEntity savedUser = userDao.save(user2);
    userDao.flush();

    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName(user2.getFirstName())
        .lastName(user2.getLastName())
        .login(user1.getLogin())
        .telegramLogin(user2.getTelegramLogin())
        .password(user2.getPassword())
        .authorities(List.of(savedAuthority.getName()))
        .build();
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserTelegramLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user1 = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    UserEntity user2 = UserEntity.builder()
        .firstName("Peter")
        .lastName("Peterov")
        .login("Pit")
        .telegramLogin("Pit_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    userDao.save(user1);
    UserEntity savedUser = userDao.save(user2);
    userDao.flush();

    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName(user2.getFirstName())
        .lastName(user2.getLastName())
        .login(user2.getLogin())
        .telegramLogin(user1.getTelegramLogin())
        .password(user2.getPassword())
        .authorities(List.of(savedAuthority.getName()))
        .build();
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserAuthorityNotExist__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    UserEntity savedUser = userDao.save(user);
    userDao.flush();

    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .login(user.getLogin())
        .telegramLogin(user.getTelegramLogin())
        .password(user.getPassword())
        .authorities(List.of("NOT_EXIST"))
        .build();
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserNotExist__thenDataNotFoundException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(List.of(savedAuthority.getName()))
        .build();
    assertThatExceptionOfType(DataNotFoundException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, TestConstant.TEST_UUID));
  }

  @Test
  public void deleteUser__whenEverythingFine__thenSaveUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UserEntity user = UserEntity.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .telegramLogin("Vano_telega")
        .password("123456")
        .authorities(Set.of(savedAuthority))
        .build();
    UserEntity savedUser = userDao.save(user);
    userDao.flush();

    UUID deletedUserId = userService.deleteUser(savedUser.getId());
    userDao.flush();

    if (userDao.existsById(savedUser.getId())) {
      fail("Пользователь все еще существует");
    }

    assertThat(deletedUserId).isEqualTo(savedUser.getId());
  }

  @Test
  public void deleteUser__whenUserNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(DataNotFoundException.class)
        .isThrownBy(() -> userService.deleteUser(TestConstant.TEST_UUID));
  }
}
