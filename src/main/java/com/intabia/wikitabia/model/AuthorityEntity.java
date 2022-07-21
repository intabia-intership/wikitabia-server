package com.intabia.wikitabia.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Сущность для работы с authority.
 */
@Entity
@Table(name = "authority")
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

  /**
   * authority id.
   */
  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  /**
   * название authority.
   */
  @Column(name = "name")
  private String name;
}
