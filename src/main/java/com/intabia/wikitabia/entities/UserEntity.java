package com.intabia.wikitabia.entities;

import java.util.UUID;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity для работы с users.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "telegram_login")
  private String telegramLogin;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "authority_id")
  private AuthorityEntity authority;
}
