package com.intabia.wikitabia.util;

import com.intabia.wikitabia.model.AuthorityEntity;
import java.util.UUID;

public interface TestConstant {
  UUID TEST_UUID = UUID.fromString("5642113c-cdbd-4833-876a-badfe4c3374e");
  AuthorityEntity USER_AUTHORITY_TO_SAVE = new AuthorityEntity(null, "USER");
  AuthorityEntity ADMIN_AUTHORITY_TO_SAVE = new AuthorityEntity(null, "ADMIN");
}
