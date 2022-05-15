//package com.intabia.dmitryi.wikitabia.webfilter;
//
//import java.io.IOException;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.stereotype.Component;
//
///**
// * Фильтр для сессинной аутентифиикации пользователя.
// * Фильтр пропускает запрос на регистрацию.
// */
//@Component
//@WebFilter(filterName = "AuthorisationFilter", urlPatterns = "/*")
//public class AuthorisationFilter implements Filter {
//  @Override
//  public void init(FilterConfig filterConfig) throws ServletException {
//  }
//
//  @Override
//  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
//                       FilterChain filterChain) throws IOException, ServletException {
//
//    HttpServletRequest request = (HttpServletRequest) servletRequest;
//    HttpServletResponse response = (HttpServletResponse) servletResponse;
//    final HttpSession session = request.getSession();
//
//    String url = request.getServletPath();
//    if (url.startsWith("/RegistrationForm.jsp")) {
//      request.getRequestDispatcher(url).forward(request, response);
//      return;
//    }
//    if (url.startsWith("/user/create")) {
//      request.getRequestDispatcher(url).forward(request, response);
//      return;
//    }
//    if (url.startsWith("/authorisation")) {
//      request.getRequestDispatcher(url).forward(request, response);
//      return;
//    }
//
//    String accessFlag = (String) session.getAttribute("access");
//    if (!"pass".equals(accessFlag)) {
//      request.getRequestDispatcher("/AuthorisationForm.jsp").forward(request, response);
//      return;
//    }
//    request.getRequestDispatcher(url).forward(request, response);
//  }
//
//  @Override
//  public void destroy() {
//
//  }
//}
