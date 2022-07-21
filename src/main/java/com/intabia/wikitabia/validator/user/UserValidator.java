package com.intabia.wikitabia.validator.user;

import com.intabia.wikitabia.dto.user.request.UserCreateRequestDto;
import com.intabia.wikitabia.dto.user.request.UserUpdateRequestDto;
import com.intabia.wikitabia.validator.user.method.AddLoginValidator;
import com.intabia.wikitabia.validator.user.method.CreateUserValidator;
import com.intabia.wikitabia.validator.user.method.DeleteUserValidator;
import com.intabia.wikitabia.validator.user.method.GetUserValidator;
import com.intabia.wikitabia.validator.user.method.UpdateUserValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * валидатор для {@link com.intabia.wikitabia.service.UserService UserService}.
 */
@Component
@RequiredArgsConstructor
public class UserValidator {
  private final CreateUserValidator createUserValidator;
  private final GetUserValidator getUserValidator;
  private final UpdateUserValidator updateUserValidator;
  private final DeleteUserValidator deleteUserValidator;
  private final AddLoginValidator addLoginValidator;

  public void createUserValidate(UserCreateRequestDto requestDto) {
    createUserValidator.validate(requestDto);
  }

  public void getUserValidate(UUID id) {
    getUserValidator.validate(id);
  }

  public void updateUserValidate(UserUpdateRequestDto requestDto, UUID id) {
    updateUserValidator.validate(requestDto, id);
  }

  public void deleteUserValidate(UUID id) {
    deleteUserValidator.validate(id);
  }

  public void addLoginValidate(UserUpdateRequestDto requestDto, UUID id) {
    addLoginValidator.validate(requestDto, id);
  }
}
