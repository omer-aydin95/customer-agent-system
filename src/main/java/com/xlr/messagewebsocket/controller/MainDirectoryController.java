package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.resource.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Main directory controller
 * Operations for non authorized users
 * Also for users who have login problems
 **/
@Controller
public class MainDirectoryController
{
  @GetMapping("/")
  public String getLogin(HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      return "redirect:/admin/dashboard";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping("/contact-admin")
  public String getContactAdmin(HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      return "redirect:/admin/dashboard";
    }
    else
    {
      return "contact-admin";
    }
  }
}
