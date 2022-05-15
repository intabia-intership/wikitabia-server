package com.intabia.wikitabia.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dto класс для передачи сущности resources между frontend и backend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
  /**
   * Идентификатор ресурса.
   */
  private UUID id;
  /**
   * Имя ресурса.
   */
  private String name;
  /**
   * URL адрес ресурса.
   */
  private String url;
  /**
   * Дата создания ресурса.
   */
  private LocalDateTime createdAt;
  /**
   * счетчик обращений к ресурсу
   */
  private Long ratingCount;
  /**
   * Идентификатор пользователя, который добавил ресурс.
   */
  private UserDto creator;
  /**
   * Список тегов.
   */
  private List<TagDto> tags;
}
