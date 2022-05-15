//package ru.intabia.dmitryi.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.UUID;
//import javax.servlet.ServletContext;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockServletContext;
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
//import com.intabia.dmitryi.wikitabia.controllers.ResourceRestController;
//import com.intabia.dmitryi.wikitabia.dto.ResourceDto;
//import com.intabia.dmitryi.wikitabia.services.interfaces.ResourceService;
//
//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {SpringMvcConfig.class})
//public class ResourceRestTest {
//
//  @Mock
//  @Autowired
//  ResourceService resourceService;
//
//  @InjectMocks
//  @Autowired
//  ResourceRestController resourceRestController;
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
//  void setUp() {
//    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApp).build();
//    MockitoAnnotations.openMocks(this);
//    this.mapper = new ObjectMapper();
//    this.mediaType =
//        new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
//            StandardCharsets.UTF_8);
//  }
//
//  @Test
//  public void contextTest() {
//    ServletContext servletContext = webApp.getServletContext();
//    assert servletContext != null;
//    Assertions.assertTrue(servletContext instanceof MockServletContext);
//    Assertions.assertNotNull(webApp.getBean("resourceRestController"));
//  }
//
//  @Test
//  public void getResourceTest() throws Exception {
//    UUID id = UUID.randomUUID();
//
//    ResourceDto resourceDto = new ResourceDto();
//    resourceDto.setId(id);
//    resourceDto.setName("1");
//    resourceDto.setUrl("1");
//
//    Mockito.when(resourceService.getResource(id)).thenReturn(resourceDto);
//    RequestBuilder request = MockMvcRequestBuilders.get("/api/resources/{id}", id);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(mapper.writeValueAsString(resourceDto),
//        result.getResponse().getContentAsString());
//  }
//
//  @Test
//  public void createResource() throws Exception {
//    ResourceDto resource = new ResourceDto();
//    resource.setName("1");
//    resource.setUrl("1");
//
//    String jsonFromResource = mapper.writeValueAsString(resource);
//
//    Mockito.when(resourceService.createResource(resource)).thenReturn(resource);
//    RequestBuilder request = MockMvcRequestBuilders.post("/api/resources")
//        .contentType(mediaType)
//        .content(jsonFromResource);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(jsonFromResource, result.getResponse().getContentAsString());
//  }
//
//  @Test
//  public void updateResource() throws Exception {
//    ResourceDto resource = new ResourceDto();
//    resource.setName("1");
//    resource.setUrl("1");
//
//    String jsonFromResource = mapper.writeValueAsString(resource);
//
//    Mockito.when(resourceService.updateResource(resource)).thenReturn(resource);
//    RequestBuilder request = MockMvcRequestBuilders.put("/api/resources")
//        .contentType(mediaType)
//        .content(jsonFromResource);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(jsonFromResource, result.getResponse().getContentAsString());
//  }
//
//  @Test
//  public void getPaginatedResources() throws Exception {
//    ResourceDto resource = new ResourceDto();
//    resource.setName("1");
//    resource.setUrl("1");
//
//    List<ResourceDto> resources = List.of(resource, resource, resource);
//
//    Mockito.when(resourceService.getResources(3, 10, null, null, null))
//        .thenReturn(new PageImpl<>(resources));
//    RequestBuilder request = MockMvcRequestBuilders
//        .get("/api/pageable-resources/{page}", 3);
//    MvcResult result = mockMvc.perform(request).andReturn();
//    Assertions.assertEquals(mapper.writeValueAsString(resources),
//        result.getResponse().getContentAsString());
//  }
//}
