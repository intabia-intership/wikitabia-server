package com.intabia.wikitabia.controller;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.service.ResourceService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * javadoc заглушка, чтобы checkstyle не ругался.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ResourceRestController {
  private ResourceService resourceService;

  @PatchMapping("/increment-rating/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public void incrementRating(@PathVariable UUID id) {
    resourceService.incrementRating(id);
  }

  @GetMapping("/resources/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResourceDto getResource(@PathVariable UUID id) {
    return resourceService.getResource(id);
  }

  @GetMapping("/pageable-resources/{page}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<ResourceDto> getResources(@PathVariable int page) {
    return resourceService.getResources(page, 10, null, null, null)
        .getContent();
  }

  @PostMapping(value = "/resources", consumes = "application/json", produces = "application/json")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResourceDto saveResource(@RequestBody ResourceDto resourceDto) {
    return resourceService.createResource(resourceDto);
  }

  @DeleteMapping("/resources/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public String deleteResource(@PathVariable UUID id) {
    resourceService.deleteResource(id);
    return "Resource was deleted";
  }

  @PutMapping(value = "/resources", consumes = "application/json", produces = "application/json")
  @PreAuthorize("hasRole('ADMIN')")
  public ResourceDto updateResource(@RequestBody ResourceDto resourceDto) {
    return resourceService.updateResource(resourceDto);
  }

}
