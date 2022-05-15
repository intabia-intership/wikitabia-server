package com.intabia.wikitabia.entities;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity для работы с tags.
 */
@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class TagEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private java.util.UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "rating_count")
  private Long ratingCount;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "tags_resources",
      joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "resource_id"))
  private List<ResourceEntity> resources;

  public TagEntity(UUID id, String name) {
    this.id = id;
    this.name = name;
  }
}
