package com.intabia.wikitabia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.exception.EntityNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.util.TestConstant;
import com.intabia.wikitabia.util.assertion.AuthorityAssert;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServiceTest extends ServiceTestWithPostgresContainer {
  @Autowired
  private AuthorityService authorityService;

  @Autowired
  private AuthorityDao authorityDao;

  @Override
  @BeforeEach
  public void cleanDB() {
    authorityDao.deleteAll();
  }

  @Test
  public void createAuthority__whenEverythingFine__thenSaveAuthority() {
    AuthorityRequestDto authorityCreate = TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO();
    AuthorityResponseDto savedAuthority = authorityService.createAuthority(authorityCreate);

    Optional<AuthorityEntity> opt = authorityDao.findById(savedAuthority.getId());
    assertThat(opt).isPresent();
    AuthorityEntity authorityInDb = opt.get();

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(authorityCreate);

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(savedAuthority);
  }

  @Test
  public void createAuthority__whenAuthorityAlreadyExist__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name(savedAuthority.getName())
        .build();
    assertThatExceptionOfType(InvalidBodyException.class)
        .isThrownBy(() -> authorityService.createAuthority(authorityCreate));
  }

  @Test
  public void getAuthority__whenEverythingFine__thenGetAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    AuthorityResponseDto getAuthority = authorityService.getAuthority(savedAuthority.getId());

    AuthorityAssert.assertThat(savedAuthority).isEqualTo(getAuthority);
  }

  @Test
  public void getAuthority__whenAuthorityNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> authorityService.getAuthority(TestConstant.DEFAULT_UUID));
  }

  @Test
  public void updateAuthority__whenEverythingFine__thenUpdateAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    AuthorityRequestDto authorityUpdate =
        TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO().withName("ADMIN");
    AuthorityResponseDto updatedAuthority =
        authorityService.updateAuthority(authorityUpdate, savedAuthority.getId());

    Optional<AuthorityEntity> opt = authorityDao.findById(savedAuthority.getId());
    assertThat(opt).isPresent();
    AuthorityEntity authorityInDb = opt.get();

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(authorityUpdate);

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(updatedAuthority);
  }

  @Test
  public void updateAuthority__whenAuthorityNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() ->
            authorityService.updateAuthority(
                TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO(), TestConstant.DEFAULT_UUID));
  }

  @Test
  public void updateAuthority__whenAuthorityNameAlreadyTaken__thenInvalidBodyException() {
    authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE().withName("ADMIN"));
    AuthorityEntity savedUserAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());

    AuthorityRequestDto authorityUpdate =
        TestConstant.DEFAULT_AUTHORITY_REQUEST_DTO().withName("ADMIN");
    assertThatExceptionOfType(InvalidBodyException.class)
        .isThrownBy(
            () -> authorityService.updateAuthority(authorityUpdate, savedUserAuthority.getId()));
  }

  @Test
  public void deleteAuthority__whenEverythingFine__thenDeleteAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.DEFAULT_AUTHORITY_TO_SAVE());
    Optional<AuthorityEntity> opt = authorityDao.findById(savedAuthority.getId());
    assertThat(opt).isPresent();

    UUID deletedEntityId = authorityService.deleteAuthority(savedAuthority.getId());

    if (authorityDao.existsById(savedAuthority.getId())) {
      fail(TestConstant.ENTITY_STILL_EXIST);
    }

    assertThat(deletedEntityId).isEqualTo(savedAuthority.getId());
  }

  @Test
  public void deleteAuthority__whenAuthorityNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> authorityService.deleteAuthority(TestConstant.DEFAULT_UUID));
  }
}
