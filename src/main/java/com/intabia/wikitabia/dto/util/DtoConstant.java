package com.intabia.wikitabia.dto.util;

/**
 * интерфейс с константами для dto.
 */
public interface DtoConstant {
  String EXAMPLE_UUID = "f191840c-96bf-4d55-9b9e-6f8052ab7038";
  String EXAMPLE_USER_FIRST_NAME = "Иван";
  String EXAMPLE_USER_LAST_NAME = "Иванов";
  String EXAMPLE_USER_LOGIN = "Vano";
  String EXAMPLE_USER_TELEGRAM_LOGIN = "Vano_telegram";
  String EXAMPLE_USER_ENCODED_PASSWORD =
      "$2a$10$qkWxcNIZNwe2/judXgJFruuK827Tp73hhOILrd2jUQPYFGCB9mMVq";
  String EXAMPLE_USER_DECODED_PASSWORD = "123456";
  String EXAMPLE_AUTHORITIES_LIST = "[{\"name\" : \"USER\"}]";
  String EXAMPLE_STRING_AUTHORITIES_LIST = "[\"USER\"]";

  String EXAMPLE_AUTHORITY_NAME = "USER";
}
