package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.Flag;
import com.xlr.messagewebsocket.constant.Statistics;
import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.constant.UserStatus;
import com.xlr.messagewebsocket.model.*;
import com.xlr.messagewebsocket.resource.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for all agent's actions
 **/
@Controller
public class AgentDirectoryController
{
  private ConversationRepository conversationRepository;
  private UserRepository userRepository;
  private UserMessageRepository userMessageRepository;
  private TicketRepository ticketRepository;

  public AgentDirectoryController(ConversationRepository conversationRepository, UserRepository userRepository,
                                  UserMessageRepository userMessageRepository, TicketRepository ticketRepository)
  {
    this.conversationRepository = conversationRepository;
    this.ticketRepository = ticketRepository;
    this.userMessageRepository = userMessageRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/agent/chat/{conversationID}")
  public String agentChat(Model model, HttpSession session, @PathVariable String conversationID)
  {
    User user = (User)session.getAttribute("user_session");
    Conversation conversation = conversationRepository.findByConversationID(conversationID);

    if(user != null && conversation != null && user.getUserRole().equals(UserRole.AGENT))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
      model.addAttribute("activeTickets", openTickets);
      // Notifications, tickets and message operations (END)

      model.addAttribute("user", user);

      return "agent/chat";
    }
    else if(user != null && conversation == null && user.getUserRole().equals(UserRole.AGENT))
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
}
