package com.intabia.wikitabia.dto.authority.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * dto для получения сущности authority в запросе.
 */
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRequestDto {
  /**
   * название authority.
   */
  @NotBlank
  private String name;
}
