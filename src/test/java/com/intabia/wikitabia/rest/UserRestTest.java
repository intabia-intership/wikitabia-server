//package ru.intabia.dmitryi.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.nio.charset.StandardCharsets;
//import java.util.UUID;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import com.intabia.dmitryi.wikitabia.config.SpringMvcConfig;
//import com.intabia.dmitryi.wikitabia.controllers.UserRestController;
//import com.intabia.dmitryi.wikitabia.dto.UserDto;
//import com.intabia.dmitryi.wikitabia.services.interfaces.UserService;
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {SpringMvcConfig.class})
//public class UserRestTest {
//
//  @InjectMocks
//  @Autowired
//  UserRestController userRestController;
//
//  @Mock
//  @Autowired
//  UserService userService;
//
//  @Autowired
//  WebApplicationContext webApp;
//
//  MockMvc mockMvc;
//
//  ObjectMapper mapper;
//
//  MediaType mediaType;
//
//  @BeforeEach
//  public void setUp() {
//    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApp).build();
//    MockitoAnnotations.openMocks(this);
//    this.mapper = new ObjectMapper();
//    this.mediaType =
//        new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
//            StandardCharsets.UTF_8);
//  }
//
//  @Test
//  public void createUser() throws Exception {
//    UserDto user = new UserDto();
//    user.setFirstName("aaaaa");
//    user.setLastName("aaaaa");
//    user.setLogin("aaaaa");
//    user.setPassword("aaaaaa");
//
//    String jsonFromUser = mapper.writeValueAsString(user);
//
//    Mockito.when(userService.createUser(user)).thenReturn(user);
//    RequestBuilder request = MockMvcRequestBuilders.post("/api/user")
//        .contentType(mediaType)
//        .content(jsonFromUser);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(jsonFromUser, result.getResponse().getContentAsString());
//  }
//
//  @Test
//  public void updateUser() throws Exception {
//    UserDto user = new UserDto();
//    user.setFirstName("aaaaa");
//    user.setLastName("aaaaa");
//    user.setLogin("aaaaa");
//    user.setPassword("aaaaaa");
//
//    String jsonFromUser = mapper.writeValueAsString(user);
//
//    Mockito.when(userService.updateUser(user)).thenReturn(user);
//    RequestBuilder request = MockMvcRequestBuilders.put("/api/user")
//        .contentType(mediaType)
//        .content(jsonFromUser);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(jsonFromUser, result.getResponse().getContentAsString());
//  }
//
//  @Test
//  public void getUser() throws Exception {
//    UUID id = UUID.randomUUID();
//
//    UserDto user = new UserDto();
//    user.setFirstName("aaaaa");
//    user.setLastName("aaaaa");
//    user.setLogin("aaaaa");
//    user.setPassword("aaaaaa");
//
//    Mockito.when(userService.getUser(id)).thenReturn(user);
//    RequestBuilder request = MockMvcRequestBuilders.get("/api/user/{id}", id);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(mapper.writeValueAsString(user),
//        result.getResponse().getContentAsString());
//  }
//}
