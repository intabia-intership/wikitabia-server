package com.intabia.wikitabia.repository;

import com.intabia.wikitabia.model.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * класс для работы с таблицей users в БД.
 */

public interface UserDao extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findUsersEntityByLoginAndPassword(String login, String password);

  Optional<UserEntity> findUserEntityByTelegramLogin(String login);

  Optional<UserEntity> findUserEntityByLogin(String login);

  boolean existsByLogin(String login);

  boolean existsByTelegramLogin(String telegramLogin);
}
