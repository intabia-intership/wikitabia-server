package com.intabia.wikitabia.services;

import com.intabia.wikitabia.model.UserEntity;
import com.intabia.wikitabia.repository.UsersDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * реализация сервиса, выдающего данные для аутентификации.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String UNKNOWN_USER_ERR_MSG = "Неизвестный пользователь: ";

    private UsersDao usersDao;

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
        UserEntity user = usersDao.findUserEntityByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(UNKNOWN_USER_ERR_MSG + username));
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getAuthorities()
                        .stream()
                        .map((authorityEntity -> new SimpleGrantedAuthority("ROLE_"+authorityEntity.getName())))
                        .collect(Collectors.toList()))
                .build();
    }
}
