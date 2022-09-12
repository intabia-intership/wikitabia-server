package com.intabia.wikitabia.controller.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.controller.security.annotation.WithAdminRole;
import com.intabia.wikitabia.controller.security.annotation.WithUserRole;
import com.intabia.wikitabia.service.AuthorityService;
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
public class AuthorityRestControllerMethodSecurityTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AuthorityService authorityService;

  @Test
  @WithAnonymousUser
  public void saveAuthority__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void saveAuthority__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void saveAuthority__whenAdminRole__thenSuccess() throws Exception {
    mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void getAuthority__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(get(TestConstant.GET_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void getAuthority__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(get(TestConstant.GET_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void getAuthority__whenAdminRole__thenSuccess() throws Exception {
    mockMvc.perform(get(TestConstant.GET_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void updateAuthority__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void updateAuthority__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void updateAuthority__whenAdminRole__thenSuccess() throws Exception {
    mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO())))
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void deleteAuthority__whenAnonymousUser__thenUnauthorized() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserRole
  public void deleteAuthority__whenUserRole__thenForbidden() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAdminRole
  public void deleteAuthority__whenAdminRole__thenSuccess() throws Exception {
    mockMvc.perform(delete(TestConstant.DELETE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
        .andExpect(status().isOk());
  }
}
