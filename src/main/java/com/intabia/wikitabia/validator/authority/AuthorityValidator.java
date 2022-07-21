package com.intabia.wikitabia.validator.authority;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.validator.authority.method.CreateAuthorityValidator;
import com.intabia.wikitabia.validator.authority.method.DeleteAuthorityValidator;
import com.intabia.wikitabia.validator.authority.method.GetAuthorityValidator;
import com.intabia.wikitabia.validator.authority.method.UpdateAuthorityValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для {@link com.intabia.wikitabia.service.AuthorityService AuthorityService}.
 */
@Component
@RequiredArgsConstructor
public class AuthorityValidator {
  private final CreateAuthorityValidator createAuthorityValidator;
  private final GetAuthorityValidator getAuthorityValidator;
  private final UpdateAuthorityValidator updateAuthorityValidator;
  private final DeleteAuthorityValidator deleteAuthorityValidator;

  public void createAuthorityValidate(AuthorityRequestDto authorityRequestDto) {
    createAuthorityValidator.validate(authorityRequestDto);
  }

  public void getAuthorityValidate(UUID id) {
    getAuthorityValidator.validate(id);
  }

  public void updateAuthorityValidate(AuthorityRequestDto authorityRequestDto, UUID id) {
    updateAuthorityValidator.validate(authorityRequestDto, id);
  }

  public void deleteAuthorityValidate(UUID id) {
    deleteAuthorityValidator.validate(id);
  }
}
