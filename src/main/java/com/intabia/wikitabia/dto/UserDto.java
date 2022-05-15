package com.intabia.wikitabia.dto;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
   * название пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "длинна 3-20")
  private String firstName;
  /**
   * фамилия пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "длинна 3-20")
  private String lastName;
  /**
   * логин пользователя.
   */
  @NotBlank
  @Size(min = 3, max = 20, message = "длинна 3-20")
  private String login;
  /**
   * пароль.
   */
  @NotBlank
  @Size(min = 5, max = 20, message = "длинна 3-20")
  private String password;
  /**
   * логин в telegram.
   */
  private String telegramLogin;
}
