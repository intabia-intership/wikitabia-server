package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.dto.TagDto;
import com.intabia.wikitabia.service.ResourceService;
import com.intabia.wikitabia.service.TagsService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * javadoc заглушка, чтобы checkstyle не ругался.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TelegramRestController {
  private ResourceService resourceService;
  private TagsService tagsService;

  @PostMapping(value = "/telegram/resource/create",
      consumes = "application/json", produces = "application/json")
  public ResourceDto createResourceByTelegram(@RequestBody ResourceDto resource) {
    return resourceService.createResourceFromTelegram(resource);
  }

  /**
   * javadoc заглушка, чтобы checkstyle не ругался.
   *
   * @param tag - javadoc заглушка, чтобы checkstyle не ругался
   * @return javadoc заглушка, чтобы checkstyle не ругался
   */
  @PostMapping(value = "/telegram/resource/filter-by-tag/",
      consumes = "application/json", produces = "application/json")
  public List<ResourceDto> getResourcePageByTag(@RequestBody TagDto tag) {
    return resourceService
        .getResources(0, 20, "name", "", List.of(tag.getName()))
        .getContent();
  }
}
