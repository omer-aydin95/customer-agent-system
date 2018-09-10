package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.constant.UserStatus;
import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.resource.AuthService;
import com.xlr.messagewebsocket.resource.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

/**
 * User login, logout operations
 * Also controller for user status such as available / busy
 **/
@Controller
public class AuthController
{
  private UserRepository userRepository;

  public AuthController(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  /**
   * Sign in operations
   **/
  @PostMapping("/")
  public String postLogin(@RequestParam("username") String userUsername,
                          @RequestParam("password") String userPassword, HttpSession session)
  {
    User user = new AuthService(userRepository).authentication(userUsername, userPassword);

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      session.setAttribute("user_session", user);

      return "redirect:/agent/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      session.setAttribute("user_session", user);

      return "redirect:/admin/dashboard";
    }
    else
    {
      return "index";
    }
  }

  /**
   * Logout operation
   **/
  @PostMapping("/logout")
  public String logout(HttpSession session)
  {
    new AuthService(userRepository).logout((User)session.getAttribute("user_session"));

    session.removeAttribute("user_session");

    return "index";
  }

  /**
   * User status changing operations
   * For example available / busy
   **/
  @PostMapping("/status")
  @ResponseStatus(value = HttpStatus.OK)
  public void status(@RequestParam("request") String request, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(request.equals(UserStatus.AVAILABLE))
    {
      user.setUserStatus(UserStatus.AVAILABLE);
    }
    else if(request.equals(UserStatus.BUSY))
    {
      user.setUserStatus(UserStatus.BUSY);
    }

    userRepository.save(user);

    session.removeAttribute("user_session");
    session.setAttribute("user_session", user);
  }
}
