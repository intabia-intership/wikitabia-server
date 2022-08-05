package com.intabia.wikitabia.util;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.model.UserEntity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestConstant {
  public static final String CREATE_AUTHORITY_ENDPOINT = "/api/authorities";
  public static final String GET_AUTHORITY_ENDPOINT = "/api/authorities/{id}";
  public static final String UPDATE_AUTHORITY_ENDPOINT = "/api/authorities/{id}";
  public static final String DELETE_AUTHORITY_ENDPOINT = "/api/authorities/{id}";

  public static final String CREATE_USER_ENDPOINT = "/api/user";
  public static final String GET_USER_ENDPOINT = "/api/user/{id}";
  public static final String UPDATE_USER_ENDPOINT = "/api/user/{id}";
  public static final String DELETE_USER_ENDPOINT = "/api/user/{id}";

  public static final String ENTITY_STILL_EXIST = "Сущность все еще находится в базе данных";

  public static final String STRING_LENGTH21 = "21symbolstring!!!!!!!";
  public static final String STRING_LENGTH2 = "sm";
  public static final String STRING_LENGTH4 = "smll";

  public static final UUID DEFAULT_UUID = UUID.fromString("5642113c-cdbd-4833-876a-badfe4c3374e");

  private static final AuthorityRequestDto DEFAULT_AUTHORITY_REQUEST_DTO = AuthorityRequestDto.builder()
      .name("USER")
      .build();

  public static AuthorityRequestDto DEFAULT_AUTHORITY_REQUEST_DTO() {
    return DEFAULT_AUTHORITY_REQUEST_DTO.toBuilder().build();
  }

  private static final UserCreateRequestDto DEFAULT_USER_CREATE_REQUEST_DTO = UserCreateRequestDto.builder()
      .firstName("Ivan")
      .lastName("Ivanov")
      .login("Vano")
      .password("123456")
      .authorities(List.of("USER"))
      .build();

  public static UserCreateRequestDto DEFAULT_USER_CREATE_REQUEST_DTO() {
    return DEFAULT_USER_CREATE_REQUEST_DTO.toBuilder().build();
  }

  private static final UserUpdateRequestDto DEFAULT_USER_UPDATE_REQUEST_DTO = UserUpdateRequestDto.builder()
      .firstName("Ivan")
      .lastName("Ivanov")
      .login("Vano")
      .password("123456")
      .telegramLogin("Vano_telega")
      .authorities(List.of("USER"))
      .build();

  public static UserUpdateRequestDto DEFAULT_USER_UPDATE_REQUEST_DTO() {
    return DEFAULT_USER_UPDATE_REQUEST_DTO.toBuilder().build();
  }

  private static final AuthorityEntity DEFAULT_AUTHORITY_TO_SAVE = new AuthorityEntity(null, "USER");

  public static AuthorityEntity DEFAULT_AUTHORITY_TO_SAVE() {
    return DEFAULT_AUTHORITY_TO_SAVE.toBuilder().build();
  }

  private static final UserEntity DEFAULT_USER_TO_SAVE = UserEntity.builder()
      .firstName("Ivan")
      .lastName("Ivanov")
      .login("Vano")
      .telegramLogin("Vano_telega")
      .password("123456")
      .authorities(Set.of(DEFAULT_AUTHORITY_TO_SAVE()))
      .build();

  public static UserEntity DEFAULT_USER_TO_SAVE() {
    return DEFAULT_USER_TO_SAVE.toBuilder().build();
  }

  private static final AuthorityResponseDto DEFAULT_AUTHORITY_RESPONSE_DTO = AuthorityResponseDto.builder()
      .id(DEFAULT_UUID)
      .name("USER")
      .build();

  public static AuthorityResponseDto DEFAULT_AUTHORITY_RESPONSE_DTO() {
    return DEFAULT_AUTHORITY_RESPONSE_DTO.toBuilder().build();
  }

  private static final UserResponseDto DEFAULT_USER_RESPONSE_DTO = UserResponseDto.builder()
      .id(DEFAULT_UUID)
      .firstName("Ivan")
      .lastName("Ivanov")
      .login("Vano")
      .password("123456")
      .telegramLogin("Vano_telega")
      .authorities(List.of(DEFAULT_AUTHORITY_RESPONSE_DTO()))
      .build();

  public static UserResponseDto DEFAULT_USER_RESPONSE_DTO() {
    return DEFAULT_USER_RESPONSE_DTO.toBuilder().build();
  }
}
