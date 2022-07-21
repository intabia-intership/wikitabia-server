package com.intabia.wikitabia.dto;

import com.intabia.wikitabia.dto.user.response.UserResponseDto;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

/**
 * Модель для работы с формами на фронте.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceModel {
  /**
   * Идентификатор ресурса.
   */
  private UUID id;
  /**
   * Имя ресурса.
   */
  @Size(min = 3, max = 20, message = "Количество символов должно быть в диапазоне 3-20")
  @NotBlank(message = "Обязательное поле")
  private String name;
  /**
   * URL адрес ресурса.
   */
  @URL(message = "поле должно быть типа url")
  private String url;
  /**
   * Дата создания ресурса.
   */
  private LocalDateTime createdAt;

  private Long ratingCount;
  /**
   * Идентификатор пользователя, который добавил ресурс.
   */
  private UserResponseDto creator;
  /**
   * Список тегов.
   */
  @Pattern(regexp = "^[u0400-u04FFa-zA-Z ]+(,[u0400-u04FFa-zA-Z ]+)*$",
      message = "теги через запятую, без пробелов")
  private String tags;
}
