package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.CreateUserDto;
import com.intabia.wikitabia.dto.UpdateUserDto;
import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

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

  @PutMapping(value = "/user/{id}", consumes = "application/json", produces = "application/json")
  public UserDto updateUser(@RequestBody @Valid UpdateUserDto updateUserDto, @PathVariable UUID id) {
    return userService.updateUser(updateUserDto, id);
  }

  @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
  public UserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
    return userService.createUser(createUserDto);
  }

  @PutMapping(value = "/telegram-login", consumes = "application/json", produces = "application/json")
  public void addTelegramLogin(@RequestBody UserDto user) {
    userService.addLogin(user);

  }
}
