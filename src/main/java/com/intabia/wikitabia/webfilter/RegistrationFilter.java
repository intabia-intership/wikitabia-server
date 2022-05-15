package com.intabia.wikitabia.webfilter;

import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.interfaces.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@RequiredArgsConstructor
public class RegistrationFilter extends GenericFilterBean {
  @NonNull
  private final UserService userService;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String login = null;
    if (authentication != null && authentication.isAuthenticated()) {
      login = authentication.getName();
    }
    if (login != null) {
      UserDto user = userService.findUserByLogin(login);
      if (user == null) {
        userService.createUser(UserDto.builder().login(login).build());
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
