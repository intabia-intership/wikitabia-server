package com.intabia.wikitabia.configuration.voter;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;


/**
 * Голосует ACCESS_DENIED если текущее время - полночь, в ином случае голосует ACCESS_ABSTAIN.
 */
public class MidnightVoter implements AccessDecisionVoter<Object> {
  private boolean isMidnight(LocalDateTime now) {
    return (now.getHour() == 0 && now.getMinute() == 0);
  }

  @Override
  public int vote(Authentication authentication, Object object, Collection collection) {
    return isMidnight(LocalDateTime.now()) ? ACCESS_DENIED : ACCESS_ABSTAIN;
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class clazz) {
    return true;
  }
}
