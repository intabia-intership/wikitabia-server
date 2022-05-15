package com.intabia.wikitabia.dto;

import com.intabia.wikitabia.entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
  private String username;
  /**
   * пароль.
   */
  private Boolean enabled;
  @NotBlank
  @Size(min = 5, max = 20, message = "длинна 3-20")
  private String password;
  /**
   * логин в telegram.
   */
  private String telegramLogin;
  /**
   * Роль.
   */
  private Authority authority;
}
