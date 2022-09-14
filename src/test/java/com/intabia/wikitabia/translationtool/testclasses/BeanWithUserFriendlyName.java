package com.intabia.wikitabia.translationtool.testclasses;

import com.intabia.wikitabia.model.annotation.UserFriendlyName;
import com.intabia.wikitabia.util.TestConstant;
import org.springframework.stereotype.Component;

@Component
@UserFriendlyName(TestConstant.BEAN_CLASS_USER_FRIENDLY_NAME)
public class BeanWithUserFriendlyName {
}
