package com.intabia.wikitabia.services;

import com.intabia.wikitabia.dao.UsersDao;
import com.intabia.wikitabia.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity user = usersDao.findUserEntityByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Неизвестный пользователь: " + username));
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getAuthority().getName())
                .build();
    }
}
