package com.intabia.wikitabia.service;

import com.intabia.wikitabia.dto.TagDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

/**
 * интерфейс сервисного слоя содержащий методы для работы с tags.
 */
public interface TagsService {
  void createTag(TagDto tagDto);

  void updateTag(TagDto tagDto);

  void deleteTag(UUID id);

  TagDto getTag(UUID id);

  List<TagDto> getTags();

  Page<TagDto> getTagsPage(int pageNumber);

  void incrementTag(UUID id);

  TagDto getTagByName(TagDto tagDto);
}
