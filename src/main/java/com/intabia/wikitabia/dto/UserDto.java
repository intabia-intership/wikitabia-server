package com.intabia.wikitabia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

/**
 * dto класс для передачи сущности users между frontend и backend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  /**
   * id пользователя.
   */
  private UUID id;

  /**
   * имя пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "Длина имени пользователя должна быть в пределах от 3 до 20 символов")
  private String firstName;

  /**
   * фамилия пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "Длина фамилии пользователя должна быть в пределах от 3 до 20 символов")
  private String lastName;

  /**
   * логин пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "Длина имени учетной записи должна быть в пределах от 3 до 20 символов")
  private String login;

  /**
   * пароль.
   */
  @NotBlank
  @Size(min = 5, max = 20, message = "Длина пароля должна быть в пределах от 5 до 20 символов")
  private String password;

  /**
   * логин в telegram.
   */
  private String telegramLogin;

  @NotEmpty(message = "Пользователь обязан иметь роль")
  private List<AuthorityDto> authorities;
}
