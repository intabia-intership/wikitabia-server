package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.TagDto;
import java.util.List;
import java.util.UUID;

import com.intabia.wikitabia.services.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagsRestController {

  private final TagsService tagsService;

  @GetMapping(value = "/page/{page}", produces = "application/json")
  public List<TagDto> getTagsPage(@PathVariable int page) {
    return tagsService.getTagsPage(page).getContent();
  }

  @PatchMapping("/increment-tag/{id}")
  public void incrementTag(@PathVariable UUID id) {
    tagsService.incrementTag(id);
  }
}
