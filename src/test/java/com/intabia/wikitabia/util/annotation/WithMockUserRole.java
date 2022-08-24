package com.intabia.wikitabia.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithMockUser;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "user", roles = {"USER"})
public @interface WithMockUserRole {
}
