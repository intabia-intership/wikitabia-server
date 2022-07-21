package com.intabia.wikitabia.controller.base;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.controller.UserRestController;
import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import com.intabia.wikitabia.service.UserService;
import com.intabia.wikitabia.util.TestConstant;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
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
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO();
    UserResponseDto mockedUserResponse = TestConstant.DEFAULT_USER_RESPONSE_DTO();
    Mockito.when(userService.createUser(userCreate))
        .thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
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
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO().withFirstName("");
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName(null);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName(TestConstant.STRING_LENGTH2);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setFirstName(TestConstant.STRING_LENGTH21);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsLastName__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO().withLastName("");
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName(null);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName(TestConstant.STRING_LENGTH2);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLastName(TestConstant.STRING_LENGTH21);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsLogin__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO().withLogin("");
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin(null);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin(TestConstant.STRING_LENGTH2);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setLogin(TestConstant.STRING_LENGTH21);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullOrOutOfBoundsPassword__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO().withPassword("");
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword(null);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword(TestConstant.STRING_LENGTH4);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setPassword(TestConstant.STRING_LENGTH21);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createUser__whenEmptyOrNullAuthorities__thenBadRequest() throws Exception {
    UserCreateRequestDto userCreate = TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO().withAuthorities(new ArrayList<>());
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());

    userCreate.setAuthorities(null);
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getUser__whenValidInput__thenOk() throws Exception {
    UserResponseDto mockedUserResponse = TestConstant.DEFAULT_USER_RESPONSE_DTO();
    Mockito.when(userService.getUser(TestConstant.DEFAULT_UUID)).thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(get(TestConstant.GET_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(userRestController, times(1))
        .getUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(userService, times(1))
        .getUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedUserResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateUser__whenValidInput__thenOk() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO();
    UserResponseDto mockedUserResponse = TestConstant.DEFAULT_USER_RESPONSE_DTO();
    Mockito.when(userService.updateUser(userUpdate, TestConstant.DEFAULT_UUID))
        .thenReturn(mockedUserResponse);

    MvcResult mvcResult =
        mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
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
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(userService, times(1))
        .updateUser(updateUserCaptor.capture(), idCaptor.capture());
    assertThat(updateUserCaptor.getValue()).usingRecursiveComparison().isEqualTo(userUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedUserResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsFirstName__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO().withFirstName("");
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName(null);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName(TestConstant.STRING_LENGTH2);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setFirstName(TestConstant.STRING_LENGTH21);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsLastName__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO().withLastName("");
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName(null);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName(TestConstant.STRING_LENGTH2);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLastName(TestConstant.STRING_LENGTH21);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsLogin__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO().withLogin("");
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin(null);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin(TestConstant.STRING_LENGTH2);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setLogin(TestConstant.STRING_LENGTH21);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullOrOutOfBoundsPassword__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO().withPassword("");
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword(null);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword(TestConstant.STRING_LENGTH4);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setPassword(TestConstant.STRING_LENGTH21);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateUser__whenEmptyOrNullAuthorities__thenBadRequest() throws Exception {
    UserUpdateRequestDto userUpdate = TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO().withAuthorities(new ArrayList<>());
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());

    userUpdate.setAuthorities(null);
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteUser__whenValidInput__thenOk() throws Exception {
    Mockito.when(userService.deleteUser(TestConstant.DEFAULT_UUID)).thenReturn(TestConstant.DEFAULT_UUID);

    MvcResult mvcResult =
        mockMvc.perform(delete(TestConstant.DELETE_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(userRestController, times(1))
        .deleteUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(userService, times(1))
        .deleteUser(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(TestConstant.DEFAULT_UUID);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }
}
