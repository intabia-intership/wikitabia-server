package com.intabia.wikitabia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
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
import org.junit.jupiter.api.BeforeEach;
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

  @Override
  @BeforeEach
  public void cleanDB() {
    userDao.deleteAll();
    authorityDao.deleteAll();
  }

  @Test
  public void createUser__whenEverythingFine__thenSaveUser() {
    authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO();
    UserResponseDto savedUser = userService.createUser(userCreate);

    Optional<UserEntity> opt = userDao.findById(savedUser.getId());
    assertThat(opt).isPresent();
    UserEntity userInDb = opt.get();

    UserAssert.assertThat(userInDb).isEqualTo(userCreate, passwordEncoder);

    UserAssert.assertThat(userInDb).isEqualTo(savedUser);
  }

  @Test
  public void createUser__whenUserLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));

    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO()
        .withFirstName("Peter")
        .withLastName("Peterov")
        .withPassword("123456");
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.createUser(userCreate));
  }

  @Test
  public void createUser__whenUserAuthorityNotExist__thenInvalidBodyException() {
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.createUser(TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO()));
  }

  @Test
  public void getUser__whenEverythingFine__thenGetUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));

    UserResponseDto getUser = userService.getUser(savedUser.getId());

    UserAssert.assertThat(savedUser).isEqualTo(getUser);
  }

  @Test
  public void getUser__whenUserNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
        userService.getUser(TestConstant.DEFAULT_UUID));
  }

  @Test
  public void updateUser__whenEverythingFine__thenUpdateUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE().withName("ADMIN"));
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));

    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO()
        .withFirstName("Peter")
        .withLastName("Peterov")
        .withLogin("Pit")
        .withTelegramLogin("Pit_telega")
        .withPassword("654321")
        .withAuthorities(List.of("ADMIN"));
    UserResponseDto updatedUser = userService.updateUser(userUpdate, savedUser.getId());

    Optional<UserEntity> opt = userDao.findById(savedUser.getId());
    assertThat(opt).isPresent();
    UserEntity userInDb = opt.get();

    UserAssert.assertThat(userInDb).isEqualTo(userUpdate, passwordEncoder);

    UserAssert.assertThat(userInDb).isEqualTo(updatedUser);
  }

  @Test
  public void updateUser__whenUserLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));
    UserEntity user = TestConstant.DEFAULT_USER_TO_SAVE()
        .withFirstName("Peter")
        .withLastName("Peterov")
        .withLogin("Pit")
        .withTelegramLogin("Pit_telega")
        .withPassword("123456")
        .withAuthorities(Set.of(savedAuthority));
    userDao.save(user);

    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO()
        .withLogin("Pit");
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserTelegramLoginAlreadyTaken__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));
    UserEntity user = TestConstant.DEFAULT_USER_TO_SAVE()
        .withFirstName("Peter")
        .withLastName("Peterov")
        .withLogin("Pit")
        .withTelegramLogin("Pit_telega")
        .withPassword("654321")
        .withAuthorities(Set.of(savedAuthority));
    userDao.save(user);

    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO()
        .withTelegramLogin("Pit_telega");
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserAuthorityNotExist__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));

    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO()
        .withLogin("Pit")
        .withTelegramLogin("Pit_telega")
        .withAuthorities(List.of("NOT_EXIST"));
    assertThatExceptionOfType(InvalidBodyException.class).isThrownBy(() ->
        userService.updateUser(userUpdate, savedUser.getId()));
  }

  @Test
  public void updateUser__whenUserNotExist__thenDataNotFoundException() {
    authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() ->
        userService.updateUser(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO(), TestConstant.DEFAULT_UUID));
  }

  @Test
  public void deleteUser__whenEverythingFine__thenSaveUser() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    UserEntity savedUser = userDao.save(TestConstant.DEFAULT_USER_TO_SAVE().withAuthorities(Set.of(savedAuthority)));

    UUID deletedUserId = userService.deleteUser(savedUser.getId());

    if (userDao.existsById(savedUser.getId())) {
      fail("Пользователь все еще существует");
    }

    assertThat(deletedUserId).isEqualTo(savedUser.getId());
  }

  @Test
  public void deleteUser__whenUserNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> userService.deleteUser(TestConstant.DEFAULT_UUID));
  }
}
