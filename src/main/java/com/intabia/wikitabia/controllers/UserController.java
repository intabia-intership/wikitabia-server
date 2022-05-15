package com.intabia.wikitabia.controllers;

import com.intabia.wikitabia.dto.ResourceDto;
import com.intabia.wikitabia.dto.UserDto;
import javax.validation.Valid;

import com.intabia.wikitabia.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  UserService userService;

  @GetMapping("/create")
  public String setModelAttributeForCreating(Model model) {
    model.addAttribute("user", new UserDto());
    return "/RegistrationForm";
  }

  @PostMapping("/create")
  public String createUser(@Valid @ModelAttribute("user") UserDto user,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "RegistrationForm";
    }
    userService.createUser(user);
    return "redirect:/Index.jsp";
  }

  @GetMapping("/delete")
  public String setModelForDeleting(Model model) {
    model.addAttribute("user", new UserDto());
    return "/DeleteUserForm";
  }

  @PostMapping("/delete")
  public String deleteUser(ResourceDto resourceDto) {
    userService.deleteUser(resourceDto.getId());
    return "redirect:/Index.jsp";
  }

  @GetMapping("/update")
  public String setModelAttributeForUpdating(Model model) {
    model.addAttribute("user", new UserDto());
    return "UpdateUserForm";
  }

  @PostMapping("/update")
  public String updateUser(@Valid @ModelAttribute("user") UserDto userDto,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "UpdateUserForm";
    }
    userService.updateUser(userDto);
    return "redirect:/Index.jsp";
  }
}
