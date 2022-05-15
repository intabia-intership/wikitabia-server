package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.dto.TagDto;
import java.util.List;

import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.ResourceService;
import com.intabia.wikitabia.services.TagsService;
import com.intabia.wikitabia.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TelegramRestController {
  private final ResourceService resourceService;
  private final TagsService tagsService;
  private final UserService userService;

  @PostMapping(value = "/telegram/resource/create",
      consumes = "application/json", produces = "application/json")
  public ResourceDto createResourceByTelegram(@RequestBody ResourceDto resource) {
    return resourceService.createResourceFromTelegram(resource);
  }

  @PostMapping(value = "/telegram/resource/filter-by-tag/",
      consumes = "application/json", produces = "application/json")
  public List<ResourceDto> getResourcePageByTag(@RequestBody TagDto tag) {
    return resourceService
        .getResources(0, 20, "name", "", List.of(tag.getName()))
        .getContent();
  }

  @PutMapping(value = "/telegram-login", consumes = "application/json", produces = "application/json")
  public void addTelegramLogin(@RequestBody UserDto user) {
    userService.addLogin(user);
  }
}
