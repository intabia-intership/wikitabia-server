package com.intabia.wikitabia.model;

import com.intabia.wikitabia.model.annotation.UserFriendlyName;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Entity для работы с users.
 */
@Entity
@UserFriendlyName("Пользователь")
@Table(name = "users")
@Data
@Builder(toBuilder = true)
@With
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements EntityWithUuid {

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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_authorities",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id"))
  private Set<AuthorityEntity> authorities;
}
