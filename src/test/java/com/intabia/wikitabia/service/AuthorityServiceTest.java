package com.intabia.wikitabia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

import com.intabia.wikitabia.dto.authority.request.AuthorityRequestDto;
import com.intabia.wikitabia.dto.authority.response.AuthorityResponseDto;
import com.intabia.wikitabia.exception.DataNotFoundException;
import com.intabia.wikitabia.exception.InvalidBodyException;
import com.intabia.wikitabia.model.AuthorityEntity;
import com.intabia.wikitabia.repository.AuthorityDao;
import com.intabia.wikitabia.util.TestConstant;
import com.intabia.wikitabia.util.assertion.AuthorityAssert;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServiceTest extends ServiceTestWithPostgresContainer {
  @Autowired
  private AuthorityService authorityService;

  @Autowired
  private AuthorityDao authorityDao;

  @Test
  public void createAuthority__whenEverythingFine__thenSaveAuthority() {
    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    AuthorityResponseDto savedAuthority = authorityService.createAuthority(authorityCreate);

    Optional<AuthorityEntity> opt = authorityDao.findById(savedAuthority.getId());
    assertThat(opt).isPresent();
    AuthorityEntity authorityInDb = opt.get();

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(authorityCreate);

    AuthorityAssert.assertThat(authorityInDb).isEqualTo(savedAuthority);
  }

  @Test
  public void createAuthority__whenAuthorityAlreadyExist__thenInvalidBodyException() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    AuthorityRequestDto authorityCreate = AuthorityRequestDto.builder()
        .name(savedAuthority.getName())
        .build();
    assertThatExceptionOfType(InvalidBodyException.class)
        .isThrownBy(() -> authorityService.createAuthority(authorityCreate));
  }

  @Test
  public void getAuthority__whenEverythingFine__thenGetAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    AuthorityResponseDto getAuthority = authorityService.getAuthority(savedAuthority.getId());

    AuthorityAssert.assertThat(savedAuthority).isEqualTo(getAuthority);
  }

  @Test
  public void getAuthority__whenAuthorityNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(DataNotFoundException.class)
        .isThrownBy(() -> authorityService.getAuthority(TestConstant.TEST_UUID));
  }

  @Test
  public void updateAuthority__whenEverythingFine__thenUpdateAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("ADMIN")
        .build();
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
    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("USER")
        .build();
    assertThatExceptionOfType(DataNotFoundException.class)
        .isThrownBy(() -> authorityService.updateAuthority(authorityUpdate, TestConstant.TEST_UUID));
  }

  @Test
  public void updateAuthority__whenAuthorityNameAlreadyTaken__thenInvalidBodyException() {
    authorityDao.save(TestConstant.ADMIN_AUTHORITY_TO_SAVE);
    AuthorityEntity savedUserAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    AuthorityRequestDto authorityUpdate = AuthorityRequestDto.builder()
        .name("ADMIN")
        .build();
    assertThatExceptionOfType(InvalidBodyException.class)
        .isThrownBy(() -> authorityService.updateAuthority(authorityUpdate, savedUserAuthority.getId()));
  }

  @Test
  public void deleteAuthority__whenEverythingFine__thenDeleteAuthority() {
    AuthorityEntity savedAuthority = authorityDao.save(TestConstant.USER_AUTHORITY_TO_SAVE);
    authorityDao.flush();

    UUID deletedEntityId = authorityService.deleteAuthority(savedAuthority.getId());
    authorityDao.flush();

    if (authorityDao.existsById(savedAuthority.getId())) {
      fail("Роль все еще существует");
    }

    assertThat(deletedEntityId).isEqualTo(savedAuthority.getId());
  }

  @Test
  public void deleteAuthority__whenAuthorityNotExist__thenDataNotFoundException() {
    assertThatExceptionOfType(DataNotFoundException.class)
        .isThrownBy(() -> authorityService.deleteAuthority(TestConstant.TEST_UUID));
  }
}
