package com.intabia.wikitabia.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * dto класс для передачи сущности tags между frontend и backend.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class TagDto {
  /**
   * id тега.
   */
  private UUID id;
  /**
   * название тега.
   */
  @NonNull
  private String name;
  /**
   * счетчик обращений к ресурсам с данным тегом
   */
  private Long ratingCount;

}
