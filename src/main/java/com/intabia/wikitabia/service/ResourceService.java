package com.intabia.wikitabia.service;

import com.intabia.wikitabia.dto.ResourceDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * интерфейс содержащий методы сервисного слоя Resources.
 */
public interface ResourceService {
  ResourceDto createResource(ResourceDto resourceDto);

  ResourceDto updateResource(ResourceDto resourceDto);

  void deleteResource(UUID id);

  Page<ResourceDto> getResources(int page, int size, String sort, String filter,
                                 List<String> filterByTag);

  ResourceDto getResource(UUID id);

  void incrementRating(UUID id);

  ResourceDto createResourceFromTelegram(ResourceDto resource);
}
