package com.intabia.wikitabia.dto;

import com.intabia.wikitabia.dto.util.DtoConstant;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * dto класс для создания пользователя.
 */
@Schema(description = "Создаваемый пользователь")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
  /**
   * имя пользователя.
   */
  @Schema(description = "имя пользователя", example = DtoConstant.EXAMPLE_USER_FIRST_NAME)
  @NotBlank
  @Size(min = 3, max = 20,
      message = "Длина имени пользователя должна быть в пределах от 3 до 20 символов")
  private String firstName;

  /**
   * фамилия пользователя.
   */
  @Schema(description = "фамилия пользователя", example = DtoConstant.EXAMPLE_USER_LAST_NAME)
  @NotBlank
  @Size(min = 3, max = 20,
      message = "Длина фамилии пользователя должна быть в пределах от 3 до 20 символов")
  private String lastName;

  /**
   * логин пользователя.
   */
  @Schema(description = "логин пользователя", example = DtoConstant.EXAMPLE_USER_LOGIN)
  @NotBlank
  @Size(min = 3, max = 20,
      message = "Длина имени учетной записи должна быть в пределах от 3 до 20 символов")
  private String login;

  /**
   * пароль.
   */
  @Schema(description = "пароль пользователя", example = DtoConstant.EXAMPLE_USER_DECODED_PASSWORD)
  @NotBlank
  @Size(min = 5, max = 20,
      message = "Длина пароля должна быть в пределах от 5 до 20 символов")
  private String password;

  /**
   * список названий authorities пользователя.
   */
  @ArraySchema(schema = @Schema(implementation = String.class),
      arraySchema = @Schema(description = "информация по ролям",
          example = DtoConstant.EXAMPLE_STRING_AUTHORITIES_LIST))
  @NotEmpty(message = "Пользователь обязан иметь роль")
  private List<String> authorities;
}
