package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.ResourceDto;
import java.util.List;
import java.util.UUID;

import com.intabia.wikitabia.services.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ResourceRestController {
  private ResourceService resourceService;

  @PatchMapping("/increment-rating/{id}")
  public void incrementRating(@PathVariable UUID id) {
    resourceService.incrementRating(id);
  }

  @GetMapping("/resources/{id}")
  public ResourceDto getResource(@PathVariable UUID id) {
    return resourceService.getResource(id);
  }

  @GetMapping("/pageable-resources/{page}")
  public List<ResourceDto> getResources(@PathVariable int page) {
    return resourceService.getResources(page, 10, null, null, null)
        .getContent();
  }

  @PostMapping(value = "/resources", consumes = "application/json", produces = "application/json")
  public ResourceDto saveResource(@RequestBody ResourceDto resourceDto) {
    return resourceService.createResource(resourceDto);
  }

  @DeleteMapping("/resources/{id}")
  public String deleteResource(@PathVariable UUID id) {
    resourceService.deleteResource(id);
    return "Resource was deleted";
  }

  @PutMapping(value = "/resources", consumes = "application/json", produces = "application/json")
  public ResourceDto updateResource(@RequestBody ResourceDto resourceDto) {
    return resourceService.updateResource(resourceDto);
  }

}
