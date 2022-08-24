package com.intabia.wikitabia.service.implementation;

import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.UserDao;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * реализация сервиса, выдающего данные для аутентификации.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private static final String UNKNOWN_USER_ERR_MSG = "Неизвестный пользователь: ";

  private final UserDao userDao;

  /**
   * получение необходимых для аутентификации данных о пользователе.
   *
   * @param username - имя пользователя
   * @return возвращает данные о пользователе, необходимые для аутентификации
   * @throws UsernameNotFoundException - если пользователь с именем username не найден
   */
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    UserEntity user = userDao.findUserEntityByLogin(username)
        .orElseThrow(() -> new UsernameNotFoundException(UNKNOWN_USER_ERR_MSG + username));
    return User.builder()
        .username(user.getLogin())
        .password(user.getPassword())
        .authorities(user.getAuthorities()
            .stream()
            .map((authorityEntity -> new SimpleGrantedAuthority(
                "ROLE_" + authorityEntity.getName())))
            .collect(Collectors.toList()))
        .build();
  }
}
