package com.intabia.wikitabia.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.TestConfig;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.dto.util.FieldRulesConstant;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.exception.response.DataNotFoundResponse;
import com.intabia.wikitabia.exception.response.InvalidBodyResponse;
import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.service.UserService;
import com.intabia.wikitabia.util.TestConstant;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = AuthorityRestController.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("controller-test")
public class UserRestControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @Autowired
  @SpyBean
  private UserRestController userRestController;

  @Test
  @WithAnonymousUser
  public void createUser__whenValidInput__thenOk() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(List.of("USER"))
        .build();
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    UserResponseDto mockedUserResponse = UserResponseDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin(null)
        .authorities(List.of(mockedAuthorityResponse))
        .build();
    Mockito.when(userService.createUser(userCreate))
        .thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreate)))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UserCreateRequestDto> createUserCaptor = ArgumentCaptor.forClass(
        UserCreateRequestDto.class);
    Mockito.verify(userRestController, times(1))
        .createUser(createUserCaptor.capture());
    assertThat(createUserCaptor.getValue()).usingRecursiveComparison().isEqualTo(userCreate);

    Mockito.verify(userService, times(1))
        .createUser(createUserCaptor.capture());
    assertThat(createUserCaptor.getValue()).usingRecursiveComparison().isEqualTo(userCreate);

    String expectedResponse = objectMapper.writeValueAsString(mockedUserResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsFirstName__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName(null);
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName("sm");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName("21symbolstring!!!!!!!");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsLastName__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("")
        .login("Vano")
        .password("123456")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName(null);
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName("sm");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName("21symbolstring!!!!!!!");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsLogin__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("")
        .password("123456")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin(null);
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin("sm");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin("21symbolstring!!!!!!!");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsPassword__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword(null);
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword("smll");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword("21symbolstring!!!!!!!");
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullAuthorities__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(new ArrayList<>())
        .build();
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setAuthorities(null);
    mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenInvalidBodyException__thenInvalidBodyResponse() throws Exception {
    UserCreateRequestDto userCreate = UserCreateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .authorities(List.of("USER"))
        .build();
    InvalidBodyException exception = new InvalidBodyException(List.of("authorities"),
        userCreate.getAuthorities().get(0), FieldRulesConstant.ROLE_MUST_EXIST_RULE);
    InvalidBodyResponse exceptionResponse = new InvalidBodyResponse(exception);
    Mockito.when(userService.createUser(userCreate))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void getUser__whenValidInput__thenOk() throws Exception {
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    UserResponseDto mockedUserResponse = UserResponseDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of(mockedAuthorityResponse))
        .build();
    Mockito.when(userService.getUser(TestConstant.TEST_UUID)).thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(get("/api/user/{id}", TestConstant.TEST_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(userRestController, times(1))
        .getUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(userService, times(1))
        .getUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedUserResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void getUser__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    DataNotFoundException exception =
        new DataNotFoundException(UserEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    Mockito.when(userService.getUser(TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(get("/api/user/{id}", TestConstant.TEST_UUID))
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateUser__whenValidInput__thenOk() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    UserResponseDto mockedUserResponse = UserResponseDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of(mockedAuthorityResponse))
        .build();
    Mockito.when(userService.updateUser(userUpdate, TestConstant.TEST_UUID))
        .thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdate)))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UserUpdateRequestDto> updateUserCaptor = ArgumentCaptor.forClass(
        UserUpdateRequestDto.class);
    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(userRestController, times(1))
        .updateUser(updateUserCaptor.capture(), idCaptor.capture());
    assertThat(updateUserCaptor.getValue()).usingRecursiveComparison().isEqualTo(userUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(userService, times(1))
        .updateUser(updateUserCaptor.capture(), idCaptor.capture());
    assertThat(updateUserCaptor.getValue()).usingRecursiveComparison().isEqualTo(userUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedUserResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsFirstName__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName(null);
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName("sm");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName("21symbolstring!!!!!!!");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsLastName__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName(null);
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName("sm");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName("21symbolstring!!!!!!!");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsLogin__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin(null);
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin("sm");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin("21symbolstring!!!!!!!");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsPassword__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword(null);
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword("smll");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword("21symbolstring!!!!!!!");
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullAuthorities__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(new ArrayList<>())
        .build();
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setAuthorities(null);
    mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    DataNotFoundException exception =
        new DataNotFoundException(UserEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    Mockito.when(userService.updateUser(userUpdate, TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult =
        mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdate)))
            .andExpect(status().isNotFound())
            .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateUser__whenInvalidBodyException__thenInvalidBodyResponse() throws Exception {
    UserUpdateRequestDto userUpdate = UserUpdateRequestDto.builder()
        .firstName("Ivan")
        .lastName("Ivanov")
        .login("Vano")
        .password("123456")
        .telegramLogin("Vano_telega")
        .authorities(List.of("USER"))
        .build();
    InvalidBodyException exception =
        new InvalidBodyException(List.of("login"), userUpdate.getLogin(), FieldRulesConstant.UNIQUE_LOGIN_RULE);
    InvalidBodyResponse exceptionResponse = new InvalidBodyResponse(exception);
    Mockito.when(userService.updateUser(userUpdate, TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult =
        mockMvc.perform(put("/api/user/{id}", TestConstant.TEST_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdate)))
            .andExpect(status().isBadRequest())
            .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void deleteUser__whenValidInput__thenOk() throws Exception {
    Mockito.when(userService.deleteUser(TestConstant.TEST_UUID)).thenReturn(TestConstant.TEST_UUID);

    MvcResult mvcResult =
        mockMvc.perform(delete("/api/user/{id}", TestConstant.TEST_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(userRestController, times(1))
        .deleteUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(userService, times(1))
        .deleteUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(TestConstant.TEST_UUID);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void deleteUser__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    DataNotFoundException exception =
        new DataNotFoundException(UserEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    Mockito.when(userService.deleteUser(TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult =
        mockMvc.perform(delete("/api/user/{id}", TestConstant.TEST_UUID))
            .andExpect(status().isNotFound())
            .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }
}
