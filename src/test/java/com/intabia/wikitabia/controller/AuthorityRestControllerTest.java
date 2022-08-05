package com.intabia.wikitabia.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.service.AuthorityService;
import com.intabia.wikitabia.util.TestConstant;
import com.intabia.wikitabia.util.annotation.WithMockAdminRole;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("controller-test")
public class AuthorityRestControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AuthorityService authorityService;

  @Autowired
  @SpyBean
  private AuthorityRestController authorityRestController;


  @Test
  @WithMockAdminRole
  public void createAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityRequestDto authorityCreate = TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO();
    AuthorityResponseDto mockedAuthorityResponse = TestConstant.DEFAULT_AUTHORITY_RESPONSE_DTO();
    Mockito.when(authorityService.createAuthority(authorityCreate))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorityCreate)))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<AuthorityRequestDto> authorityCaptor =
        ArgumentCaptor.forClass(AuthorityRequestDto.class);
    Mockito.verify(authorityRestController, times(1))
        .createAuthority(authorityCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityCreate);

    Mockito.verify(authorityService, times(1))
        .createAuthority(authorityCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityCreate);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  @WithMockAdminRole
  public void createAuthority__whenEmptyOrNullName__thenBadRequest() throws Exception {
    AuthorityRequestDto authorityCreate = TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO().withName("");
    mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityCreate)))
        .andExpect(status().isBadRequest());

    authorityCreate.setName(null);
    mockMvc.perform(post(TestConstant.CREATE_AUTHORITY_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockAdminRole
  public void getAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityResponseDto mockedAuthorityResponse = TestConstant.DEFAULT_AUTHORITY_RESPONSE_DTO();
    Mockito.when(authorityService.getAuthority(TestConstant.DEFAULT_UUID))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(get(TestConstant.GET_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(authorityRestController, times(1))
        .getAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(authorityService, times(1))
        .getAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  @WithMockAdminRole
  public void updateAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityRequestDto authorityUpdate = TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO();
    AuthorityResponseDto mockedAuthorityResponse = TestConstant.DEFAULT_AUTHORITY_RESPONSE_DTO();
    Mockito.when(authorityService.updateAuthority(authorityUpdate, TestConstant.DEFAULT_UUID))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorityUpdate)))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<AuthorityRequestDto> authorityCaptor =
        ArgumentCaptor.forClass(AuthorityRequestDto.class);
    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(authorityRestController, times(1))
        .updateAuthority(authorityCaptor.capture(), idCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(authorityService, times(1))
        .updateAuthority(authorityCaptor.capture(), idCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  @WithMockAdminRole
  public void updateAuthority__whenEmptyOrNullName__thenBadRequest() throws Exception {
    AuthorityRequestDto authorityUpdate = TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO().withName("");
    mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isBadRequest());

    authorityUpdate.setName(null);
    mockMvc.perform(put(TestConstant.UPDATE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockAdminRole
  public void deleteAuthority__whenValidInput__thenOk() throws Exception {
    Mockito.when(authorityService.deleteAuthority(TestConstant.DEFAULT_UUID))
        .thenReturn(TestConstant.DEFAULT_UUID);

    MvcResult mvcResult =
        mockMvc.perform(delete(TestConstant.DELETE_AUTHORITY_ENDPOINT, TestConstant.DEFAULT_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(authorityRestController, times(1))
        .deleteAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    Mockito.verify(authorityService, times(1))
        .deleteAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.DEFAULT_UUID);

    String expectedResponse = objectMapper.writeValueAsString(TestConstant.DEFAULT_UUID);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }
}
