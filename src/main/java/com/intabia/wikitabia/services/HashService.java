package com.intabia.wikitabia.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

import com.intabia.wikitabia.exceptions.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * класс-сервис для хеширования паролей.
 * использует данные из application properties.
 */
@Component("hashService")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashService {

  private Environment env;

  /**
   * метод для генерации хешкода на основании String пароля.
   *
   * @param password пароль введенный пользователем.
   * @return хешированный пароль.
   */
  public String hashPassword(String password) {
    try {
      String algorithm = env.getProperty("hashAlgorithm");
      String salt = env.getProperty("salt");
      password += salt;
      byte[] inputValue = password.getBytes();
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
      messageDigest.update(inputValue);
      byte[] digestBytes = messageDigest.digest();
      return DatatypeConverter.printHexBinary(digestBytes);

    } catch (NoSuchAlgorithmException e) {
      throw new CustomException("Ошибка получения хеш суммы");
    }
  }
}
