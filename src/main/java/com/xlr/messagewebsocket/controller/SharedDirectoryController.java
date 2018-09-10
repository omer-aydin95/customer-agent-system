package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.Flag;
import com.xlr.messagewebsocket.constant.Statistics;
import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.constant.UserStatus;
import com.xlr.messagewebsocket.model.*;
import com.xlr.messagewebsocket.resource.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for all admin's and agent's actions
 **/
@Controller
@SuppressWarnings("Duplicates")
public class SharedDirectoryController
{
  private ConversationRepository conversationRepository;
  private UserRepository userRepository;
  private UserMessageRepository userMessageRepository;
  private TicketRepository ticketRepository;

  public SharedDirectoryController(ConversationRepository conversationRepository, UserRepository userRepository,
                                  UserMessageRepository userMessageRepository, TicketRepository ticketRepository)
  {
    this.conversationRepository = conversationRepository;
    this.ticketRepository = ticketRepository;
    this.userMessageRepository = userMessageRepository;
    this.userRepository = userRepository;
  }

  @GetMapping({"/admin/user/agent/all", "/agent/user/agent/all"})
  public String getAgentAll(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.AGENT, UserStatus.DELETED
      );
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "All Agents");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/user/agent/online", "/agent/user/agent/online"})
  public String getAgentOnline(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(user.getUserID(), UserRole.AGENT,
              UserStatus.LOGOUT);
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "Online Agents");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/user/agent/offline", "/agent/user/agent/offline"})
  public String getAgentOffline(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatus(user.getUserID(), UserRole.AGENT,
              UserStatus.LOGOUT);
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "Offline Agents");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/user/admin/all", "/agent/user/admin/all"})
  public String getAdminAll(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.ADMIN, UserStatus.DELETED
      );
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "All Admins");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/user/admin/online", "/agent/user/admin/online"})
  public String getAdminOnline(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.ADMIN, UserStatus.LOGOUT
      );
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "Online Admins");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/user/admin/offline", "/agent/user/admin/offline"})
  public String getAdminOffline(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<User> users = userRepository.findByUserIDIsNotAndUserRoleAndUserStatus(user.getUserID(), UserRole.ADMIN,
              UserStatus.LOGOUT);
      model.addAttribute("users", users);

      model.addAttribute("user", user);
      model.addAttribute("pageTitle", "Offline Admins");

      return "shared/show-users";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/dashboard", "/agent/dashboard"})
  public String getDashboard(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      // Operations for charts (BEGIN)
      int[] accDaysA = new DatabaseInteractionService(conversationRepository)
              .getAcceptedConversationsForDays(user);
      model.addAttribute("accDaysA", accDaysA);

      int[] accDaysI = new DatabaseInteractionService(conversationRepository)
              .getIgnoredConversationsForDays(user);
      model.addAttribute("accDaysI", accDaysI);

      int[] aacDays = new DatabaseInteractionService(userMessageRepository).getMessagesForDays(user);
      model.addAttribute("aacDays", aacDays);

      int[] tDaysO = new DatabaseInteractionService(ticketRepository).getOpenedTicketsForDays(user);
      model.addAttribute("tDaysO", tDaysO);

      int[] tDaysA = new DatabaseInteractionService(ticketRepository).getAssignedTicketsForDays(user);
      model.addAttribute("tDaysA", tDaysA);

      int[] tDaysC = new DatabaseInteractionService(ticketRepository).getClosedTicketsForDays(user);
      model.addAttribute("tDaysC", tDaysC);
      // Operations for charts (END)

      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> allMessages = userMessageRepository.findByToUser(user);
      model.addAttribute("allMessages", allMessages);

      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
      model.addAttribute("activeTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS, user
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS, user
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Statistics calculations for users (BEGIN)
      int[] uStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      uStatistics[0] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_USERS, user
      );
      uStatistics[1] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.AGENTS, Statistics.FOR_USERS, user
      );
      uStatistics[2] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ADMINS, Statistics.FOR_USERS, user
      );
      model.addAttribute("uStatistics", uStatistics);
      // Statistics calculations for users (END)

      List<User> agents = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.AGENT, UserStatus.LOGOUT
      );
      model.addAttribute("onlineAgents", agents);

      List<User> admins = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.ADMIN, UserStatus.LOGOUT
      );
      model.addAttribute("onlineAdmins", admins);

      model.addAttribute("user", user);

      return "shared/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      // Operations for charts (BEGIN)
      int[] accDaysA = new DatabaseInteractionService(conversationRepository)
              .getAcceptedConversationsForDays();
      model.addAttribute("accDaysA", accDaysA);

      int[] accDaysI = new DatabaseInteractionService(conversationRepository)
              .getIgnoredConversationsForDays();
      model.addAttribute("accDaysI", accDaysI);

      int[] aacDays = new DatabaseInteractionService(userMessageRepository).getMessagesForDays();
      model.addAttribute("aacDays", aacDays);

      int[] tDaysO = new DatabaseInteractionService(ticketRepository).getOpenedTicketsForDays();
      model.addAttribute("tDaysO", tDaysO);

      int[] tDaysA = new DatabaseInteractionService(ticketRepository).getAssignedTicketsForDays();
      model.addAttribute("tDaysA", tDaysA);

      int[] tDaysC = new DatabaseInteractionService(ticketRepository).getClosedTicketsForDays();
      model.addAttribute("tDaysC", tDaysC);
      // Operations for charts (END)

      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> allMessages = userMessageRepository.findByToUser(user);
      model.addAttribute("allMessages", allMessages);

      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> closedTickets = ticketRepository.findByClosedBy(user);
      model.addAttribute("closedTickets", closedTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Statistics calculations for users (BEGIN)
      int[] uStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      uStatistics[0] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_USERS
      );
      uStatistics[1] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.AGENTS, Statistics.FOR_USERS
      );
      uStatistics[2] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ADMINS, Statistics.FOR_USERS
      );
      model.addAttribute("uStatistics", uStatistics);
      // Statistics calculations for users (END)

      List<User> agents = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.AGENT, UserStatus.LOGOUT
      );
      model.addAttribute("onlineAgents", agents);

      List<User> admins = userRepository.findByUserIDIsNotAndUserRoleAndUserStatusIsNot(
              user.getUserID(), UserRole.ADMIN, UserStatus.LOGOUT
      );
      model.addAttribute("onlineAdmins", admins);

      model.addAttribute("user", user);

      return "shared/dashboard";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/message/all", "/agent/message/all"})
  public String getMessageAll(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && (user.getUserRole().equals(UserRole.ADMIN) || user.getUserRole().equals(UserRole.AGENT)))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      if(user.getUserRole().equals(UserRole.ADMIN))
      {
        List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
        model.addAttribute("activeTickets", activeTickets);

        List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
        model.addAttribute("openTickets", openTickets);
      }
      else
      {
        List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
        model.addAttribute("activeTickets", openTickets);
      }
      // Notifications, tickets and message operations (END)

      List<UserMessage> userMessages = userMessageRepository.findByToUserOrderBySendDateDesc(user);
      List<UserMessage> distinctMessages = new ArrayList<>();

      for(UserMessage userMessage: userMessages)
      {
        if(!distinctMessages.contains(userMessage))
        {
          distinctMessages.add(userMessage);
        }
      }
      model.addAttribute("messages", distinctMessages);

      model.addAttribute("user", user);

      return "shared/all-messages";
    }
    else
    {
      return "index";
    }
  }

  @GetMapping({"/admin/statistics/conversations-with-customer", "/agent/statistics/conversations-with-customer"})
  public String getConversationWithCustomer(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
      model.addAttribute("activeTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS, user
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS, user
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<Conversation> accepted = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
              startDate, endDate, Flag.CONVERSATION_ACCEPTED, user
      );
      model.addAttribute("accepted", accepted.size());
      List<Conversation> ignored = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
              startDate, endDate, Flag.CONVERSATION_IGNORED, user
      );
      model.addAttribute("ignored", ignored.size());
      // calculations for charts (END)

      List<Conversation> conversations = conversationRepository.findByUser(user);
      model.addAttribute("conversations", conversations);

      model.addAttribute("user", user);

      return "shared/conversations-with-customer";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Statistics calculations for users (BEGIN)
      int[] uStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      uStatistics[0] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_USERS
      );
      uStatistics[1] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.AGENTS, Statistics.FOR_USERS
      );
      uStatistics[2] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ADMINS, Statistics.FOR_USERS
      );
      model.addAttribute("uStatistics", uStatistics);
      // Statistics calculations for users (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<Conversation> accepted = conversationRepository.findByConversationDateBetweenAndConversationStatus(
              startDate, endDate, Flag.CONVERSATION_ACCEPTED
      );
      model.addAttribute("accepted", accepted.size());

      List<Conversation> ignored = conversationRepository.findByConversationDateBetweenAndConversationStatus(
              startDate, endDate, Flag.CONVERSATION_IGNORED
      );
      model.addAttribute("ignored", ignored.size());
      // calculations for charts (END)

      List<Conversation> conversations = conversationRepository.findAll();
      model.addAttribute("conversations", conversations);

      model.addAttribute("user", user);

      return "shared/conversations-with-customer";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping("/admin/statistics/conversations-with-customer")
  @ResponseBody
  public IStatisticsObject postConversationWithCustomer(@RequestParam("forStatistics") String forStatistics, @RequestParam("startDate") String startDate,
                                                        @RequestParam("endDate") String endDate)
  {
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(forStatistics.equals(Statistics.FOR_AGENT_CUSTOMER_CONV))
    {
      List<Conversation> accepted = conversationRepository.findByConversationDateBetweenAndConversationStatus(
              startDate1, endDate1, Flag.CONVERSATION_ACCEPTED
      );
      List<Conversation> ignored = conversationRepository.findByConversationDateBetweenAndConversationStatus(
              startDate1, endDate1, Flag.CONVERSATION_IGNORED
      );

      return new ACCStatistics(accepted.size(), ignored.size());
    }
    else
    {
      return null;
    }
  }

  @PostMapping("/agent/statistics/conversations-with-customer")
  @ResponseBody
  public IStatisticsObject postConversationWithCustomer(@RequestParam("userID") String userID, @RequestParam("forStatistics") String forStatistics,
                                                        @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate)
  {
    User detailUser = userRepository.findByUserID(userID);
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(detailUser != null && forStatistics.equals(Statistics.FOR_AGENT_CUSTOMER_CONV))
    {
      List<Conversation> accepted = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
              startDate1, endDate1, Flag.CONVERSATION_ACCEPTED, detailUser
      );
      List<Conversation> ignored = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
              startDate1, endDate1, Flag.CONVERSATION_IGNORED, detailUser
      );

      return new ACCStatistics(accepted.size(), ignored.size());
    }
    else
    {
      return null;
    }
  }

  @GetMapping({"/admin/statistics/conversations-with-admin", "/agent/statistics/conversations-with-admin"})
  public String getConversationWithAdmin(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
      model.addAttribute("activeTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS, user
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS, user
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<UserMessage> messages = userMessageRepository.findBySendDateBetweenAndFromUser(startDate, endDate, user);
      model.addAttribute("messages", messages.size());
      // calculations for charts (END)

      List<UserMessage> userMessages = userMessageRepository.findByFromUser(user);
      model.addAttribute("userMessages", userMessages);

      model.addAttribute("user", user);

      return "shared/conversations-with-admin";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Statistics calculations for users (BEGIN)
      int[] uStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      uStatistics[0] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_USERS
      );
      uStatistics[1] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.AGENTS, Statistics.FOR_USERS
      );
      uStatistics[2] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ADMINS, Statistics.FOR_USERS
      );
      model.addAttribute("uStatistics", uStatistics);
      // Statistics calculations for users (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<UserMessage> messages = userMessageRepository.findBySendDateBetween(startDate, endDate);
      model.addAttribute("messages", messages.size());
      // calculations for charts (END)

      List<UserMessage> userMessages = userMessageRepository.findAll();
      model.addAttribute("userMessages", userMessages);

      model.addAttribute("user", user);

      return "shared/conversations-with-admin";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping("/admin/statistics/conversations-with-admin")
  @ResponseBody
  public IStatisticsObject postConversationWithAdmin(@RequestParam("forStatistics") String forStatistics, @RequestParam("startDate") String startDate,
                                                     @RequestParam("endDate") String endDate)
  {
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(forStatistics.equals(Statistics.FOR_AGENT_ADMIN_CONV))
    {
      List<UserMessage> messages = userMessageRepository.findBySendDateBetween(startDate1, endDate1);

      return new AACStatistics(messages.size());
    }
    else
    {
      return null;
    }
  }

  @PostMapping("/agent/statistics/conversations-with-admin")
  @ResponseBody
  public IStatisticsObject postConversationWithAdmin(@RequestParam("userID") String userID, @RequestParam("forStatistics") String forStatistics,
                                                     @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate)
  {
    User detailUser = userRepository.findByUserID(userID);
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(detailUser != null && forStatistics.equals(Statistics.FOR_AGENT_ADMIN_CONV))
    {
      List<UserMessage> messages = userMessageRepository.findBySendDateBetweenAndFromUser(startDate1, endDate1, detailUser);

      return new AACStatistics(messages.size());
    }
    else
    {
      return null;
    }
  }

  @GetMapping({"/admin/statistics/tickets", "/agent/statistics/tickets"})
  public String getTickets(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> openTickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());
      model.addAttribute("activeTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV, user
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV, user
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS, user
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS, user
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS, user
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<Ticket> opened = ticketRepository.findByCreatedAtBetweenAndOpenedBy(startDate, endDate, user.getUserEmail());
      model.addAttribute("opened", opened.size());

      List<Ticket> assigned = ticketRepository.findByAssignedAtBetweenAndOpenedBy(startDate, endDate, user.getUserEmail());
      model.addAttribute("assigned", assigned.size());

      List<Ticket> closed = ticketRepository.findByClosedAtBetweenAndOpenedBy(startDate, endDate, user.getUserEmail());
      model.addAttribute("closed", closed.size());
      // calculations for charts (END)

      List<Ticket> tickets = ticketRepository.findByOpenedBy(user.getUserEmail());
      model.addAttribute("tickets", tickets);

      model.addAttribute("user", user);

      return "shared/tickets";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN))
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      // Statistics calculations for agent - customer conversations (BEGIN)
      int[] accStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      accStatistics[0] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
              Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_TICKETS
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
              Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Statistics calculations for users (BEGIN)
      int[] uStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      uStatistics[0] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ALL, Statistics.FOR_USERS
      );
      uStatistics[1] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.AGENTS, Statistics.FOR_USERS
      );
      uStatistics[2] = new DatabaseInteractionService(userRepository).getStatisticsFor(
              Statistics.ADMINS, Statistics.FOR_USERS
      );
      model.addAttribute("uStatistics", uStatistics);
      // Statistics calculations for users (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<Ticket> opened = ticketRepository.findByCreatedAtBetween(startDate, endDate);
      model.addAttribute("opened", opened.size());

      List<Ticket> assigned = ticketRepository.findByAssignedAtBetween(startDate, endDate);
      model.addAttribute("assigned", assigned.size());

      List<Ticket> closed = ticketRepository.findByClosedAtBetween(startDate, endDate);
      model.addAttribute("closed", closed.size());
      // calculations for charts (END)

      List<Ticket> tickets = ticketRepository.findAll();
      model.addAttribute("tickets", tickets);

      model.addAttribute("user", user);

      return "shared/tickets";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping("/admin/statistics/tickets")
  @ResponseBody
  public IStatisticsObject postTickets(@RequestParam("forStatistics") String forStatistics, @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate)
  {
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(forStatistics.equals(Statistics.FOR_TICKETS))
    {
      List<Ticket> opened = ticketRepository.findByCreatedAtBetween(startDate1, endDate1);
      List<Ticket> assigned = ticketRepository.findByAssignedAtBetween(startDate1, endDate1);
      List<Ticket> closed = ticketRepository.findByClosedAtBetween(startDate1, endDate1);

      return new TStatistics(opened.size(), assigned.size(), closed.size());
    }
    else
    {
      return null;
    }
  }

  @PostMapping("/agent/statistics/tickets")
  @ResponseBody
  public IStatisticsObject postTickets(@RequestParam("userID") String userID, @RequestParam("forStatistics") String forStatistics,
                                       @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate)
  {
    User detailUser = userRepository.findByUserID(userID);
    LocalDateTime startDate1 = LocalDateTime.parse(startDate);
    LocalDateTime endDate1 = LocalDateTime.parse(endDate);

    if(detailUser != null && forStatistics.equals(Statistics.FOR_TICKETS))
    {
      List<Ticket> opened = ticketRepository.findByCreatedAtBetweenAndOpenedBy(startDate1, endDate1, detailUser.getUserEmail());
      List<Ticket> assigned = ticketRepository.findByAssignedAtBetweenAndOpenedBy(startDate1, endDate1, detailUser.getUserEmail());
      List<Ticket> closed = ticketRepository.findByClosedAtBetweenAndOpenedBy(startDate1, endDate1, detailUser.getUserEmail());

      return new TStatistics(opened.size(), assigned.size(), closed.size());
    }
    else
    {
      return null;
    }
  }
}
