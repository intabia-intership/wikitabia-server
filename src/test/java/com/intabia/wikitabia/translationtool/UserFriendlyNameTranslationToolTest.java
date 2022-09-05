package com.intabia.wikitabia.translationtool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.intabia.wikitabia.model.util.UserFriendlyNameTranslationTool;
import com.intabia.wikitabia.translationtool.config.UserFriendlyNameTranslationToolTestConfig;
import com.intabia.wikitabia.translationtool.testclasses.BeanWithUserFriendlyName;
import com.intabia.wikitabia.translationtool.testclasses.BeanWithoutUserFriendlyName;
import com.intabia.wikitabia.translationtool.testclasses.EntityWithUserFriendlyName;
import com.intabia.wikitabia.translationtool.testclasses.EntityWithoutUserFriendlyName;
import com.intabia.wikitabia.util.TestConstant;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = UserFriendlyNameTranslationToolTestConfig.class)
@ActiveProfiles("translation-tool-test")
public class UserFriendlyNameTranslationToolTest {
  @Test
  public void whenEntityClassHasUserFriendlyNameAnnotation__thenDictionaryContainsThisName() {
    assertThat(
        UserFriendlyNameTranslationTool.getUserFriendlyName(EntityWithUserFriendlyName.class))
        .isEqualTo(TestConstant.ENTITY_CLASS_USER_FRIENDLY_NAME);
  }

  @Test
  public void whenEntityClassHasNotUserFriendlyNameAnnotation__thenDictionaryThrowsException() {
    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() ->
            UserFriendlyNameTranslationTool.getUserFriendlyName(
                EntityWithoutUserFriendlyName.class));
  }

  @Test
  public void whenBeanClassHasUserFriendlyNameAnnotation__thenDictionaryContainsThisName() {
    assertThat(
        UserFriendlyNameTranslationTool.getUserFriendlyName(BeanWithUserFriendlyName.class))
        .isEqualTo(TestConstant.BEAN_CLASS_USER_FRIENDLY_NAME);
  }

  @Test
  public void whenBeanClassHasNotUserFriendlyNameAnnotation__thenDictionaryThrowsException() {
    assertThatExceptionOfType(NoSuchElementException.class)
        .isThrownBy(() ->
            UserFriendlyNameTranslationTool.getUserFriendlyName(
                BeanWithoutUserFriendlyName.class));
  }
}
