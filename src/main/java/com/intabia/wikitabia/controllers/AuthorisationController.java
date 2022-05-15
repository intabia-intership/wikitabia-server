package com.intabia.wikitabia.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.intabia.wikitabia.dto.UserDto;
import com.intabia.wikitabia.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthorisationController {

  UserService userService;

  @PostMapping("/authorisation")
  public String authorisation(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    if (login == null || password == null) {
      return "AuthorisationForm";
    }
    UserDto user = (userService.authorisation(login, password));
    if (user == null) {
      return "AuthorisationForm";
    }
    session.setAttribute("access", "pass");
    session.setAttribute("user", user);
    return "redirect:/Index.jsp";
  }

}
