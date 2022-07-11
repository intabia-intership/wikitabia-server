package com.intabia.wikitabia.repository;

import java.util.Optional;
import java.util.UUID;

import com.intabia.wikitabia.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * класс для работы с таблицей users в БД.
 */

public interface UsersDao extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findUsersEntityByLoginAndPassword(String login, String password);

  Optional<UserEntity> findUserEntityByTelegramLogin(String login);

  Optional<UserEntity> findUserEntityByLogin(String login);
}
