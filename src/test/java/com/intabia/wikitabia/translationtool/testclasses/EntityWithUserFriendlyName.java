package com.intabia.wikitabia.translationtool.testclasses;

import com.intabia.wikitabia.model.annotation.UserFriendlyName;
import com.intabia.wikitabia.util.TestConstant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@UserFriendlyName(TestConstant.ENTITY_CLASS_USER_FRIENDLY_NAME)
public class EntityWithUserFriendlyName {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;
}
