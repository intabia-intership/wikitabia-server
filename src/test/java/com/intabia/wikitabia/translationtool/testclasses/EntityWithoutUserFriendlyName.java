package com.intabia.wikitabia.translationtool.testclasses;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EntityWithoutUserFriendlyName {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;
}
