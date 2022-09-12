package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.TagDto;
import com.intabia.wikitabia.service.TagsService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * javadoc заглушка, чтобы checkstyle не ругался.
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagsRestController {

  private final TagsService tagsService;

  @GetMapping(value = "/page/{page}", produces = "application/json")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<TagDto> getTagsPage(@PathVariable int page) {
    return tagsService.getTagsPage(page).getContent();
  }

  @PatchMapping("/increment-tag/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public void incrementTag(@PathVariable UUID id) {
    tagsService.incrementTag(id);
  }
}
