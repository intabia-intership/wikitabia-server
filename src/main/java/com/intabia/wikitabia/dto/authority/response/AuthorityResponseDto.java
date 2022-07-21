package com.intabia.wikitabia.dto.authority.response;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * dto для отправления сущности authority в ответе.
 */
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityResponseDto {
  /**
   * authority id.
   */
  @NotNull
  private UUID id;
  /**
   * название authority.
   */
  @NotBlank
  private String name;
}
