package com.intabia.wikitabia.controllers;

import java.util.UUID;
import javax.validation.Valid;

import com.intabia.wikitabia.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intabia.wikitabia.dto.UserDto;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserRestController {
  private UserService userService;

  @GetMapping("/user/{id}")
  public UserDto getUser(@PathVariable UUID id) {
    return userService.getUser(id);
  }

  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return "User was deleted";
  }

  @PutMapping(value = "/user", consumes = "application/json", produces = "application/json")
  public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
    return userService.updateUser(userDto);
  }

  @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
  public UserDto createUser(@RequestBody @Valid UserDto userDto) {
    return userService.createUser(userDto);
  }

  @PutMapping(value = "/telegram-login", consumes = "application/json", produces = "application/json")
  public void addTelegramLogin(@RequestBody UserDto user) {
    userService.addLogin(user);

  }
}
