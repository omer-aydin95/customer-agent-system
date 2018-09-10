package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.*;
import com.xlr.messagewebsocket.model.*;
import com.xlr.messagewebsocket.resource.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for all admin's actions
 **/
@Controller
@SuppressWarnings("Duplicates")
public class AdminDirectoryController
{
  private ConversationRepository conversationRepository;
  private UserRepository userRepository;
  private UserMessageRepository userMessageRepository;
  private TicketRepository ticketRepository;

  public AdminDirectoryController(ConversationRepository conversationRepository, UserRepository userRepository,
                                  UserMessageRepository userMessageRepository, TicketRepository ticketRepository)
  {
    this.conversationRepository = conversationRepository;
    this.ticketRepository = ticketRepository;
    this.userMessageRepository = userMessageRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/admin/add-new-user")
  public String getAddNewUser(Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
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

      model.addAttribute("user", user);
      model.addAttribute("userID", EncryptionService.getUniqueID());
      model.addAttribute("saveStatus", Flag.SAVE_STATUS_NULL);

      return "admin/add-new-user";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping(value = "/admin/add-new-user", consumes = {"multipart/form-data"})
  public String postAddNewUser(@RequestParam("userFirstname") String userFirstname, @RequestParam("userLastname") String userLastname,
                               @RequestParam("userEmail") String userEmail, @RequestParam("userGender") String userGender,
                               @RequestParam("userID") String userID, @RequestParam("userRole") String userRole,
                               @RequestParam("username") String username, @RequestParam("password") String password,
                               @RequestParam("profilePicture")MultipartFile profilePicture, Model model, HttpSession session)
      throws IOException
  {
    User user = (User)session.getAttribute("user_session");

    User newUser = new User(userID, userFirstname, userLastname, UserStatus.LOGOUT,
        username, EncryptionService.getEncryptedPassword(password), userRole,
        userEmail, LocalDateTime.now(), userGender, null);

    if(!profilePicture.isEmpty())
    {
      String pathForHtmlAndDB = FileController.saveProfilePicture(profilePicture, userID);

      newUser.setProfilePicture(pathForHtmlAndDB);
    }
    else if(profilePicture.isEmpty() && userGender.equals(Flag.GENDER_MALE))
    {
      newUser.setProfilePicture(FilePath.MALE_DEFAULT_AVATAR);
    }
    else if(profilePicture.isEmpty() && userGender.equals(Flag.GENDER_FEMALE))
    {
      newUser.setProfilePicture(FilePath.FEMALE_DEFAULT_AVATAR);
    }

    userRepository.save(newUser);

    // Notifications, tickets and message operations (BEGIN)
    List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
    model.addAttribute("unreadMessages", unreadMessages);

    List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
    model.addAttribute("activeTickets", activeTickets);

    List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
    model.addAttribute("openTickets", openTickets);
    // Notifications, tickets and message operations (END)

    model.addAttribute("user", user);
    model.addAttribute("userID", EncryptionService.getUniqueID());
    model.addAttribute("saveStatus", Flag.SAVE_STATUS_SUCCESSFUL);

    return "admin/add-new-user";
  }

  @GetMapping("/admin/user/detail/{userID}")
  public String getUserDetail(@PathVariable String userID, Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");
    User detailUser = userRepository.findByUserID(userID);

    if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser != null)
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
          Statistics.ALL, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[1] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.TODAY, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[2] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.THIS_WEEK, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[3] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.THIS_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[4] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[5] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[6] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      accStatistics[7] = new DatabaseInteractionService(conversationRepository).getStatisticsFor(
          Statistics.THIS_YEAR, Statistics.FOR_AGENT_CUSTOMER_CONV, detailUser
      );
      model.addAttribute("accStatistics", accStatistics);
      // Statistics calculations for agent - customer conversations (END)

      // Statistics calculations for agent - admin conversations (BEGIN)
      int[] aacStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      aacStatistics[0] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.ALL, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[1] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.TODAY, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[2] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.THIS_WEEK, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[3] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.THIS_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[4] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.LAST_3_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[5] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.LAST_6_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[6] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.LAST_9_MONTH, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      aacStatistics[7] = new DatabaseInteractionService(userMessageRepository).getStatisticsFor(
          Statistics.THIS_YEAR, Statistics.FOR_AGENT_ADMIN_CONV, detailUser
      );
      model.addAttribute("aacStatistics", aacStatistics);
      // Statistics calculations for agent - admin conversations (END)

      // Statistics calculations for tickets (BEGIN)
      int[] tStatistics = {0, 0, 0, 0, 0, 0, 0, 0};
      tStatistics[0] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
          Statistics.ALL, Statistics.FOR_TICKETS, detailUser
      );
      tStatistics[1] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
          Statistics.OPENED_TICKETS, Statistics.FOR_TICKETS, detailUser
      );
      tStatistics[2] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
          Statistics.ASSIGNED_TICKETS, Statistics.FOR_TICKETS, detailUser
      );
      tStatistics[3] = new DatabaseInteractionService(ticketRepository).getStatisticsFor(
          Statistics.CLOSED_TICKETS, Statistics.FOR_TICKETS, detailUser
      );
      model.addAttribute("tStatistics", tStatistics);
      // Statistics calculations for tickets (END)

      // Calculations for charts (BEGIN)
      LocalDateTime startDate = LocalDateTime.now();
      startDate = startDate.minusHours(startDate.getHour());
      LocalDateTime endDate = startDate.plusDays(6);

      List<Conversation> accepted = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
          startDate, endDate, Flag.CONVERSATION_ACCEPTED, detailUser
      );
      model.addAttribute("accepted", accepted.size());

      List<Conversation> ignored = conversationRepository.findByConversationDateBetweenAndConversationStatusAndUser(
          startDate, endDate, Flag.CONVERSATION_IGNORED, detailUser
      );
      model.addAttribute("ignored", ignored.size());

      List<UserMessage> messages = userMessageRepository.findBySendDateBetweenAndFromUser(startDate, endDate, detailUser);
      model.addAttribute("messages", messages.size());

      List<Ticket> opened = ticketRepository.findByCreatedAtBetweenAndOpenedBy(startDate, endDate, detailUser.getUserEmail());
      model.addAttribute("opened", opened.size());

      List<Ticket> assigned = ticketRepository.findByAssignedAtBetweenAndOpenedBy(startDate, endDate, detailUser.getUserEmail());
      model.addAttribute("assigned", assigned.size());

      List<Ticket> closed = ticketRepository.findByClosedAtBetweenAndOpenedBy(startDate, endDate, detailUser.getUserEmail());
      model.addAttribute("closed", closed.size());
      // calculations for charts (END)

      List<Conversation> conversations = conversationRepository.findByUser(detailUser);
      model.addAttribute("conversations", conversations);

      List<UserMessage> userMessages = userMessageRepository.findByFromUser(detailUser);
      model.addAttribute("userMessages", userMessages);

      List<Ticket> tickets = ticketRepository.findByOpenedBy(detailUser.getUserEmail());
      model.addAttribute("tickets", tickets);

      model.addAttribute("user", user);
      model.addAttribute("detailUser", detailUser);

      return "admin/user-detail";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser == null)
    {
      return "redirect:/admin/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping("/admin/user/detail")
  @ResponseBody
  public IStatisticsObject postUserDetail(@RequestParam("userID") String userID, @RequestParam("forStatistics") String forStatistics,
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
    else if(detailUser != null && forStatistics.equals(Statistics.FOR_AGENT_ADMIN_CONV))
    {
      List<UserMessage> messages = userMessageRepository.findBySendDateBetweenAndFromUser(startDate1, endDate1, detailUser);

      return new AACStatistics(messages.size());
    }
    else if(detailUser != null && forStatistics.equals(Statistics.FOR_TICKETS))
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

  @PostMapping("/admin/user/delete")
  @ResponseStatus(HttpStatus.OK)
  public void postUserDelete(@RequestParam("userID") String userID)
  {
    User deleteUser = userRepository.findByUserID(userID);

    if(deleteUser != null)
    {
      deleteUser.setUserStatus(UserStatus.DELETED);

      userRepository.save(deleteUser);
    }
  }

  @GetMapping("/admin/user/edit/{userID}")
  public String getEditUser(@PathVariable String userID, Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");
    User detailUser = userRepository.findByUserID(userID);

    if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser != null)
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      model.addAttribute("user", user);
      model.addAttribute("detailUser", detailUser);

      return "admin/edit-user";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser == null)
    {
      return "redirect:/admin/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping(value = "/admin/user/edit", consumes = {"multipart/form-data"})
  public String postEditUser(@RequestParam("userFirstname") String userFirstname, @RequestParam("userLastname") String userLastname,
                             @RequestParam("userEmail") String userEmail, @RequestParam("userGender") String userGender,
                             @RequestParam("userID") String userID, @RequestParam("userRole") String userRole,
                             @RequestParam("username") String username, @RequestParam("profilePicture")MultipartFile profilePicture,
                             Model model, HttpSession session)
      throws IOException
  {
    User user = (User)session.getAttribute("user_session");

    User editUser = userRepository.findByUserID(userID);
    editUser.setUserFirstname(userFirstname);
    editUser.setUserLastname(userLastname);
    editUser.setUserEmail(userEmail);
    editUser.setGender(userGender);
    editUser.setUserRole(userRole);
    editUser.setUsername(username);

    if(!profilePicture.isEmpty())
    {
      if(!editUser.getProfilePicture().equals(FilePath.MALE_DEFAULT_AVATAR) &&
          !editUser.getProfilePicture().equals(FilePath.FEMALE_DEFAULT_AVATAR))
      {
        FileController.deleteFile(editUser.getProfilePicture());
      }

      String pathForHtmlAndDB = FileController.saveProfilePicture(profilePicture, userID);

      editUser.setProfilePicture(pathForHtmlAndDB);
    }

    userRepository.save(editUser);

    // Notifications, tickets and message operations (BEGIN)
    List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
    model.addAttribute("unreadMessages", unreadMessages);

    List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
    model.addAttribute("activeTickets", activeTickets);

    List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
    model.addAttribute("openTickets", openTickets);
    // Notifications, tickets and message operations (END)

    model.addAttribute("user", user);
    model.addAttribute("detailUser", editUser);
    model.addAttribute("saveStatus", Flag.SAVE_STATUS_SUCCESSFUL);

    return "admin/edit-user";
  }

  @GetMapping("/admin/user/reset-password/{userID}")
  public String getResetPassword(@PathVariable String userID, Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");
    User detailUser = userRepository.findByUserID(userID);

    if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser != null)
    {
      // Notifications, tickets and message operations (BEGIN)
      List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
      model.addAttribute("unreadMessages", unreadMessages);

      List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
      model.addAttribute("activeTickets", activeTickets);

      List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
      model.addAttribute("openTickets", openTickets);
      // Notifications, tickets and message operations (END)

      model.addAttribute("user", user);
      model.addAttribute("detailUser", detailUser);

      return "admin/reset-password";
    }
    else if(user != null && user.getUserRole().equals(UserRole.ADMIN) && detailUser == null)
    {
      return "redirect:/admin/dashboard";
    }
    else if(user != null && user.getUserRole().equals(UserRole.AGENT))
    {
      return "redirect:/agent/dashboard";
    }
    else
    {
      return "index";
    }
  }

  @PostMapping("/admin/user/reset-password")
  public String postResetPassword(@RequestParam("userID") String userID, @RequestParam("password") String password,
                                  Model model, HttpSession session)
  {
    User user = (User)session.getAttribute("user_session");

    User editUser = userRepository.findByUserID(userID);

    editUser.setPassword(EncryptionService.getEncryptedPassword(password));

    userRepository.save(editUser);

    // Notifications, tickets and message operations (BEGIN)
    List<UserMessage> unreadMessages = userMessageRepository.findByToUserAndIsRead(user, Flag.MESSAGE_UNREAD);
    model.addAttribute("unreadMessages", unreadMessages);

    List<Ticket> activeTickets = ticketRepository.findByAssigneeAndClosedByIsNull(user);
    model.addAttribute("activeTickets", activeTickets);

    List<Ticket> openTickets = ticketRepository.findByAssigneeIsNullAndClosedByIsNull();
    model.addAttribute("openTickets", openTickets);
    // Notifications, tickets and message operations (END)

    model.addAttribute("user", user);
    model.addAttribute("detailUser", editUser);
    model.addAttribute("saveStatus", Flag.SAVE_STATUS_SUCCESSFUL);

    return "admin/reset-password";
  }
}
