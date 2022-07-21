package com.intabia.wikitabia.services;

import com.intabia.wikitabia.dto.TagDto;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.mappers.TagsMapper;
import com.intabia.wikitabia.model.TagEntity;
import com.intabia.wikitabia.repository.TagsDao;
import com.intabia.wikitabia.services.service.TagsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * реализация сервиса для работы с сущностями tags.
 */
@Service("tagsService")
@AllArgsConstructor
@Transactional
public class TagsServiceImpl implements TagsService {
  private static final String TAG_NOT_FOUNT_ERR_MSG = "Тег не найден";

  private final TagsMapper tagsMapper;
  private final TagsDao tagsDao;

  @Override
  public void createTag(TagDto tagDto) {
    TagEntity tagEntity = tagsMapper.dtoToEntity(tagDto);
    tagsDao.save(tagEntity);
  }

  @Override
  public void updateTag(TagDto tagDto) {
    TagEntity tagEntity = tagsMapper.dtoToEntity(tagDto);
    tagsDao.save(tagEntity);
  }

  @Override
  public void deleteTag(UUID id) {
    tagsDao.deleteById(id);
  }

  @Override
  public TagDto getTag(UUID id) {
    TagEntity tag = tagsDao.findById(id)
        .orElseThrow(() -> new DataNotFoundException(TAG_NOT_FOUNT_ERR_MSG));
    return tagsMapper.entityToDto(tag);
  }

  @Override
  public List<TagDto> getTags() {
    Iterable<TagEntity> tagsFromTable = tagsDao.findAll();
    return StreamSupport.stream(tagsFromTable.spliterator(), false)
        .map(tagsMapper::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public Page<TagDto> getTagsPage(int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, 9, Sort.by("name"));
    return tagsDao.findAll(pageable).map(tagsMapper::entityToDto);
  }

  @Override
  public void incrementTag(UUID id) {
    TagEntity tag = tagsDao.findById(id)
            .orElseThrow(() -> new DataNotFoundException(TAG_NOT_FOUNT_ERR_MSG));
    if (tag.getRatingCount() == null) {
      tag.setRatingCount(0L);
    }
    tag.setRatingCount(tag.getRatingCount() + 1L);
    tagsDao.save(tag);
  }

  public TagDto getTagByName(TagDto tagDto) {
    return tagsMapper.entityToDto(tagsDao.findByName(tagDto.getName()));
  }
}
