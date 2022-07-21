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
import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.dto.util.FieldRulesConstant;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.exception.response.DataNotFoundResponse;
import com.intabia.wikitabia.exception.response.InvalidBodyResponse;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.service.AuthorityService;
import com.intabia.wikitabia.util.TestConstant;
import java.nio.charset.StandardCharsets;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = AuthorityRestController.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
@ContextConfiguration(classes = {TestConfig.class})
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
  public void saveAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    Mockito.when(authorityService.createAuthority(authorityCreate))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(post("/api/authorities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorityCreate)))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<AuthorityRequestDto> authorityCaptor =
        ArgumentCaptor.forClass(AuthorityRequestDto.class);
    Mockito.verify(authorityRestController, times(1))
        .saveAuthority(authorityCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityCreate);

    Mockito.verify(authorityService, times(1))
        .createAuthority(authorityCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityCreate);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void saveAuthority__whenEmptyOrNullName__thenBadRequest() throws Exception {
    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name("")
        .build();
    mockMvc.perform(post("/api/authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityCreate)))
        .andExpect(status().isBadRequest());

    authorityCreate.setName(null);
    mockMvc.perform(post("/api/authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityCreate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void saveAuthority__whenInvalidBodyException__thenInvalidBodyResponse() throws Exception {
    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    InvalidBodyException exception =
        new InvalidBodyException(List.of("name"), authorityCreate.getName(),
            FieldRulesConstant.UNIQUE_ROLE_RULE);
    InvalidBodyResponse exceptionResponse = new InvalidBodyResponse(exception);
    Mockito.when(authorityService.createAuthority(authorityCreate))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(post("/api/authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityCreate)))
        .andExpect(status().isBadRequest())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void getAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    Mockito.when(authorityService.getAuthority(TestConstant.TEST_UUID))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(get("/api/authorities/{id}", TestConstant.TEST_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(authorityRestController, times(1))
        .getAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(authorityService, times(1))
        .getAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void getAuthority__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    DataNotFoundException exception =
        new DataNotFoundException(AuthorityEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    Mockito.when(authorityService.getAuthority(TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(get("/api/authorities/{id}", TestConstant.TEST_UUID))
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateAuthority__whenValidInput__thenOk() throws Exception {
    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    AuthorityResponseDto mockedAuthorityResponse = AuthorityResponseDto.builder()
        .id(TestConstant.TEST_UUID)
        .name("USER")
        .build();
    Mockito.when(authorityService.updateAuthority(authorityUpdate, TestConstant.TEST_UUID))
        .thenReturn(mockedAuthorityResponse);

    MvcResult mvcResult =
        mockMvc.perform(put("/api/authorities/{id}", TestConstant.TEST_UUID)
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
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(authorityService, times(1))
        .updateAuthority(authorityCaptor.capture(), idCaptor.capture());
    assertThat(authorityCaptor.getValue()).usingRecursiveComparison().isEqualTo(authorityUpdate);
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(mockedAuthorityResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateAuthority__whenEmptyOrNullName__thenBadRequest() throws Exception {
    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("")
        .build();
    mockMvc.perform(put("/api/authorities/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isBadRequest());

    authorityUpdate.setName(null);
    mockMvc.perform(put("/api/authorities/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateAuthority__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    DataNotFoundException exception =
        new DataNotFoundException(AuthorityEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    Mockito.when(authorityService.updateAuthority(authorityUpdate, TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(put("/api/authorities/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void updateAuthority__whenInvalidBodyException__thenInvalidBodyResponse() throws Exception {
    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    InvalidBodyException exception =
        new InvalidBodyException(List.of("name"), authorityUpdate.getName(),
            FieldRulesConstant.UNIQUE_ROLE_RULE);
    InvalidBodyResponse exceptionResponse = new InvalidBodyResponse(exception);
    Mockito.when(authorityService.updateAuthority(authorityUpdate, TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(put("/api/authorities/{id}", TestConstant.TEST_UUID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(authorityUpdate)))
        .andExpect(status().isBadRequest())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void deleteAuthority__whenValidInput__thenOk() throws Exception {
    Mockito.when(authorityService.deleteAuthority(TestConstant.TEST_UUID))
        .thenReturn(TestConstant.TEST_UUID);

    MvcResult mvcResult =
        mockMvc.perform(delete("/api/authorities/{id}", TestConstant.TEST_UUID))
            .andExpect(status().isOk())
            .andReturn();

    ArgumentCaptor<UUID> idCaptor = ArgumentCaptor.forClass(UUID.class);
    Mockito.verify(authorityRestController, times(1))
        .deleteAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    Mockito.verify(authorityService, times(1))
        .deleteAuthority(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(TestConstant.TEST_UUID);

    String expectedResponse = objectMapper.writeValueAsString(TestConstant.TEST_UUID);
    String actualResponse = mvcResult.getResponse().getContentAsString();
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }

  @Test
  public void deleteAuthority__whenDataNotFoundException__thenDataNotFoundResponse() throws Exception {
    DataNotFoundException exception =
        new DataNotFoundException(AuthorityEntity.class, TestConstant.TEST_UUID);
    DataNotFoundResponse exceptionResponse = new DataNotFoundResponse(exception);
    Mockito.when(authorityService.deleteAuthority(TestConstant.TEST_UUID))
        .thenThrow(exception);

    MvcResult mvcResult = mockMvc.perform(delete("/api/authorities/{id}", TestConstant.TEST_UUID))
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedResponse = objectMapper.writeValueAsString(exceptionResponse);
    String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
    assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
  }
}
