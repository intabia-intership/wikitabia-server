package com.intabia.wikitabia.dto;

import com.intabia.wikitabia.dto.util.DtoConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dto класс для передачи сущности authority между frontend и backend.
 */
@Schema(description = "Роль")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {
  /**
   * название authority.
   */
  @Schema(description = "название роли", example = DtoConstant.EXAMPLE_AUTHORITY_NAME)
  @NotBlank
  private String name;
}
