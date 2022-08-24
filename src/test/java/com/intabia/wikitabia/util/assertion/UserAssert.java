package com.intabia.wikitabia.util.assertion;

import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import java.util.stream.Collectors;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserAssert extends AbstractAssert<UserAssert, UserEntity> {

  public UserAssert(UserEntity userEntity) {
    super(userEntity, UserAssert.class);
  }

  public static UserAssert assertThat(UserEntity actual) {
    return new UserAssert(actual);
  }

  public UserAssert isEqualTo(UserCreateRequestDto other, PasswordEncoder encoder) {
    Assertions.assertThat(actual.getFirstName()).isEqualTo(other.getFirstName());
    Assertions.assertThat(actual.getLastName()).isEqualTo(other.getLastName());
    Assertions.assertThat(actual.getLogin()).isEqualTo(other.getLogin());
    Assertions.assertThat(encoder.matches(other.getPassword(), actual.getPassword())).isTrue();
    Assertions.assertThat(
            actual.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()))
        .hasSameSizeAs(other.getAuthorities())
        .hasSameElementsAs(other.getAuthorities());
    return this;
  }

  public UserAssert isEqualTo(UserUpdateRequestDto other, PasswordEncoder encoder) {
    Assertions.assertThat(actual.getFirstName()).isEqualTo(other.getFirstName());
    Assertions.assertThat(actual.getLastName()).isEqualTo(other.getLastName());
    Assertions.assertThat(actual.getLogin()).isEqualTo(other.getLogin());
    Assertions.assertThat(actual.getTelegramLogin()).isEqualTo(other.getTelegramLogin());
    Assertions.assertThat(encoder.matches(other.getPassword(), actual.getPassword())).isTrue();
    Assertions.assertThat(
            actual.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()))
        .hasSameSizeAs(other.getAuthorities())
        .hasSameElementsAs(other.getAuthorities());
    return this;
  }

  public UserAssert isEqualTo(UserResponseDto other) {
    Assertions.assertThat(actual.getId()).isEqualTo(other.getId());
    Assertions.assertThat(actual.getFirstName()).isEqualTo(other.getFirstName());
    Assertions.assertThat(actual.getLastName()).isEqualTo(other.getLastName());
    Assertions.assertThat(actual.getLogin()).isEqualTo(other.getLogin());
    Assertions.assertThat(actual.getTelegramLogin()).isEqualTo(other.getTelegramLogin());
    Assertions.assertThat(actual.getPassword()).isEqualTo(other.getPassword());
    Assertions.assertThat(
            actual.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toList()))
        .hasSameSizeAs(
            other.getAuthorities())
        .hasSameElementsAs(
            other.getAuthorities().stream().map(AuthorityResponseDto::getName).collect(Collectors.toList()));
    return this;
  }
}
