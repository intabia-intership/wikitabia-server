package com.intabia.wikitabia.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity для работы с resources.
 */
@Entity
@Table(name = "resources")
@Data
public class ResourceEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private java.util.UUID id;

  @Column(name = "name")
  private String name;
  @Column(name = "url")

  private String url;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "rating_count")
  private Long ratingCount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creator_id")
  private UserEntity creator;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "tags_resources",
      joinColumns = @JoinColumn(name = "resource_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<TagEntity> tags;

}
