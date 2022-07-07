package com.intabia.wikitabia.filter;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * реализация RequestMatcher, позволяющая KeycloakAuthenticationProcessingFilter
 * пропускать запросы с basic auth дальше по цепочке фильтров,
 * а также отдавать приоритет авторизации с помощью jwt.
 */
public class IgnoreBasicAuthRequestHeaderRequestMatcher implements RequestMatcher {
    public boolean matches(HttpServletRequest request) {
        Enumeration<String> authorizationHeaders = request.getHeaders("Authorization");
        if (!authorizationHeaders.hasMoreElements()) {
            return false;
        }

        boolean hasBasicAuth = false;
        while (authorizationHeaders.hasMoreElements()) {
            String header = authorizationHeaders.nextElement();
            if (header.startsWith("Bearer ")) {
               return true;
            } else if (header.startsWith("Basic ")) {
                hasBasicAuth = true;
            }
        }

        return !hasBasicAuth;
    }
}
