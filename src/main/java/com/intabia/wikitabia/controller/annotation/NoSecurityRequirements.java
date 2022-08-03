package com.intabia.wikitabia.controller.annotation;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * аннотация, позволяющая отключить глобальные SecurityRequirement.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@SecurityRequirements
public @interface NoSecurityRequirements {
}
