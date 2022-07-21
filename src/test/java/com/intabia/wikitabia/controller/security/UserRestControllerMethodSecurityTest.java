package com.intabia.wikitabia.controller.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.controller.security.annotation.WithAdminRole;
import com.intabia.wikitabia.controller.security.annotation.WithUserRole;
import com.intabia.wikitabia.service.UserService;
import com.intabia.wikitabia.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("controller-test")
public class UserRestControllerMethodSecurityTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @Test
  @WithAnonymousUser
  public void createUser__whenAnonymousUser__thenOk() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithUserRole
  public void createUser__whenUserRole__thenOk() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAdminRole
  public void createUser__whenAdminRole__thenOk() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_USER_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_CREATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void getUser__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(get(TestConstant.GET_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void getUser__whenUserRole__thenOk() throws Exception {
    mockMvc.perform(get(TestConstant.GET_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isOk());
  }

  @Test
  @WithAdminRole
  public void getUser__whenAdminRole__thenOk() throws Exception {
    mockMvc.perform(get(TestConstant.GET_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void updateUser__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void updateUser__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void updateUser__whenAdminRole__thenOk() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_USER_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void deleteUser__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void deleteUser__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void deleteUser__whenAdminRole__thenOk() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_USER_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void addLogin__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(put(TestConstant.ADD_LOGIN_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void addLogin__whenUserRole__thenOk() throws Exception {
    mockMvc.perform(put(TestConstant.ADD_LOGIN_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAdminRole
  public void addLogin__whenAdminRole__thenOk() throws Exception {
    mockMvc.perform(put(TestConstant.ADD_LOGIN_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_USER_UPDATE_REQUEST_DTO())))
        .andExpect(status().isOk());
  }
}
