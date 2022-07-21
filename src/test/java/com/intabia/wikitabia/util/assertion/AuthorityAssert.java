package com.intabia.wikitabia.util.assertion;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.model.AuthorityEntity;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class AuthorityAssert extends AbstractAssert<AuthorityAssert, AuthorityEntity> {
  public AuthorityAssert(AuthorityEntity authorityEntity) {
    super(authorityEntity, AuthorityAssert.class);
  }

  public static AuthorityAssert assertThat(AuthorityEntity actual) {
    return new AuthorityAssert(actual);
  }

  public AuthorityAssert isEqualTo(AuthorityRequestDto other) {
    Assertions.assertThat(actual.getName()).isEqualTo(other.getName());
    return this;
  }

  public AuthorityAssert isEqualTo(AuthorityResponseDto other) {
    Assertions.assertThat(actual.getId()).isEqualTo(other.getId());
    Assertions.assertThat(actual.getName()).isEqualTo(other.getName());
    return this;
  }
}
