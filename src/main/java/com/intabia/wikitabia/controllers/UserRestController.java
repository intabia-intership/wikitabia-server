package com.intabia.wikitabia.controllers;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.intabia.wikitabia.exceptions.AuthenticationFailedException;
import com.intabia.wikitabia.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.intabia.wikitabia.dto.UserDto;

@RestController
@AllArgsConstructor
public class UserRestController {
  private UserService userService;

  @GetMapping("/user/{id}")
  public UserDto getUser(@PathVariable UUID id) {
    return userService.getUser(id);
  }

  @DeleteMapping("/user/{id}")
  public void deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
  }

  @PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
  public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
    return userService.updateUser(userDto);
  }

  @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
  public UserDto createUser(@RequestBody @Valid UserDto userDto) {
    return userService.saveUser(userDto);
  }

  @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto register(@RequestBody UserDto user) {
    return userService.saveUser(user);
  }
}
