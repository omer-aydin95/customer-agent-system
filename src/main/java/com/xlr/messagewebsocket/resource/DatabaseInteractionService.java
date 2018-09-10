package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.constant.Flag;
import com.xlr.messagewebsocket.constant.Statistics;
import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.model.Conversation;
import com.xlr.messagewebsocket.model.Ticket;
import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.model.UserMessage;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Mixed database operations
 *
 * Written in order to not make crowd in controllers
 **/
@SuppressWarnings("Duplicates")
public class DatabaseInteractionService
{
  private ConversationRepository conversationRepository;
  private UserMessageRepository userMessageRepository;
  private TicketRepository ticketRepository;
  private UserRepository userRepository;

  public DatabaseInteractionService(ConversationRepository conversationRepository)
  {
    this.conversationRepository = conversationRepository;
  }

  public DatabaseInteractionService(UserMessageRepository userMessageRepository)
  {
    this.userMessageRepository = userMessageRepository;
  }

  public DatabaseInteractionService(TicketRepository ticketRepository)
  {
    this.ticketRepository = ticketRepository;
  }

  public DatabaseInteractionService(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  /**
   * Find week number of the given local datetime
   *
   * For example 28. week of the given date's year
   **/
  private int getWeekOfYear(LocalDateTime localDateTime)
  {
    WeekFields weekFields = WeekFields.of(Locale.getDefault());

    return localDateTime.get(weekFields.weekOfWeekBasedYear());
  }

  /**
   * Find current week number of the year
   *
   * For example 28. week of the current year
   **/
  private int getCurrentWeekOfYear()
  {
    Calendar calendar = Calendar.getInstance();

    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * Find agent - customer conversations according to acceptance status (ACCEPTED)
   * And calculate number of conversations per days
   * Then return them in an array
   * Array's indexes are corresponding to days of week
   **/
  public int[] getAcceptedConversationsForDays()
  {
    int[] daysAccepted = {0, 0, 0, 0, 0, 0, 0};

    List<Conversation> conversations = conversationRepository
        .findByConversationStatus(Flag.CONVERSATION_ACCEPTED);

    for(Conversation conversation: conversations)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysAccepted[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysAccepted[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysAccepted[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysAccepted[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysAccepted[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysAccepted[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysAccepted[6]++;
      }
    }

    return daysAccepted;
  }

  /**
   * Find agent - customer conversations according to acceptance status (ACCEPTED) for specific user
   * And calculate number of conversations per days
   * Then return them in an array
   * Array's indexes are corresponding to days of week
   **/
  public int[] getAcceptedConversationsForDays(User user)
  {
    int[] daysAccepted = {0, 0, 0, 0, 0, 0, 0};

    List<Conversation> conversations = conversationRepository
        .findByUserAndConversationStatus(user, Flag.CONVERSATION_ACCEPTED);

    for(Conversation conversation: conversations)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysAccepted[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysAccepted[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysAccepted[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysAccepted[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysAccepted[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysAccepted[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysAccepted[6]++;
      }
    }

    return daysAccepted;
  }

  /**
   * Find agent - customer conversations according to acceptance status (IGNORED)
   * And calculate number of conversations per days
   * Then return them in an array
   * Array's indexes are corresponding to days of week
   **/
  public int[] getIgnoredConversationsForDays()
  {
    int[] daysIgnored = {0, 0, 0, 0, 0, 0, 0};

    List<Conversation> conversations = conversationRepository
        .findByConversationStatus(Flag.CONVERSATION_IGNORED);

    for(Conversation conversation: conversations)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysIgnored[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysIgnored[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysIgnored[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysIgnored[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysIgnored[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysIgnored[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysIgnored[6]++;
      }
    }

    return daysIgnored;
  }

  /**
   * Find agent - customer conversations according to acceptance status (IGNORED) for specific user
   * And calculate number of conversations per days
   * Then return them in an array
   * Array's indexes are corresponding to days of week
   **/
  public int[] getIgnoredConversationsForDays(User user)
  {
    int[] daysIgnored = {0, 0, 0, 0, 0, 0, 0};

    List<Conversation> conversations = conversationRepository
        .findByUserAndConversationStatus(user, Flag.CONVERSATION_IGNORED);

    for(Conversation conversation: conversations)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysIgnored[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysIgnored[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysIgnored[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysIgnored[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysIgnored[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysIgnored[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()) &&
          conversation.getConversationDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysIgnored[6]++;
      }
    }

    return daysIgnored;
  }

  /**
   * Find agent - admin conversations
   * And calculate number of conversations per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getMessagesForDays()
  {
    int[] days = {0, 0, 0, 0, 0, 0, 0};

    List<UserMessage> userMessages = userMessageRepository.findAll();

    for(UserMessage userMessage: userMessages)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        days[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        days[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        days[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        days[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        days[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        days[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        days[6]++;
      }
    }

    return days;
  }

  /**
   * Find agent - admin conversations for specific user
   * And calculate number of conversations per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getMessagesForDays(User user)
  {
    int[] days = {0, 0, 0, 0, 0, 0, 0};

    List<UserMessage> userMessages = userMessageRepository.findByFromUserOrToUser(user, user);

    for(UserMessage userMessage: userMessages)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        days[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        days[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        days[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        days[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        days[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        days[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()) &&
          userMessage.getSendDate().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        days[6]++;
      }
    }

    return days;
  }

  /**
   * Find opened tickets
   * And calculate number of opened tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getOpenedTicketsForDays()
  {
    int[] daysOpened = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findAll();

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysOpened[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysOpened[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysOpened[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysOpened[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysOpened[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysOpened[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysOpened[6]++;
      }
    }

    return daysOpened;
  }

  /**
   * Find opened tickets for specific user
   * And calculate number of opened tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getOpenedTicketsForDays(User user)
  {
    int[] daysOpened = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID());

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysOpened[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysOpened[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysOpened[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysOpened[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysOpened[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysOpened[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysOpened[6]++;
      }
    }

    return daysOpened;
  }

  /**
   * Find assigned tickets
   * And calculate number of assigned tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getAssignedTicketsForDays()
  {
    int[] daysAssigned = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findByAssignedAtIsNotNull();

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysAssigned[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysAssigned[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysAssigned[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysAssigned[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysAssigned[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysAssigned[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysAssigned[6]++;
      }
    }

    return daysAssigned;
  }

  /**
   * Find assigned tickets for specific user
   * And calculate number of assigned tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getAssignedTicketsForDays(User user)
  {
    int[] daysAssigned = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findByOpenedByAndAssigneeIsNotNullAndClosedByIsNull(user.getUserID());

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysAssigned[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysAssigned[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysAssigned[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysAssigned[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysAssigned[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysAssigned[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysAssigned[6]++;
      }
    }

    return daysAssigned;
  }

  /**
   * Find closed tickets
   * And calculate number of closed tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getClosedTicketsForDays()
  {
    int[] daysClosed = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findByClosedAtIsNotNull();

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysClosed[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysClosed[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysClosed[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysClosed[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysClosed[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysClosed[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysClosed[6]++;
      }
    }

    return daysClosed;
  }

  /**
   * Find closed tickets for specific user
   * And calculate number of closed tickets per day
   * Then return in an array
   * Array indexes are corresponding to days of week
   **/
  public int[] getClosedTicketsForDays(User user)
  {
    int[] daysClosed = {0, 0, 0, 0, 0, 0, 0};

    List<Ticket> tickets = ticketRepository.findByOpenedByAndClosedByIsNotNull(user.getUserID());

    for(Ticket ticket: tickets)
    {
      if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.MONDAY)
      {
        daysClosed[0]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.TUESDAY)
      {
        daysClosed[1]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.WEDNESDAY)
      {
        daysClosed[2]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.THURSDAY)
      {
        daysClosed[3]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.FRIDAY)
      {
        daysClosed[4]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SATURDAY)
      {
        daysClosed[5]++;
      }
      else if(getCurrentWeekOfYear() == getWeekOfYear(ticket.getClosedAt()) &&
          ticket.getCreatedAt().getDayOfWeek() == DayOfWeek.SUNDAY)
      {
        daysClosed[6]++;
      }
    }

    return daysClosed;
  }

  /**
   * Get agent - customer conversations statistics for given statistics type
   **/
  private int getAgentCustomerConversationsStatistics(String statisticsType)
  {
    int count = 0;

    List<Conversation> conversations = conversationRepository.findAll();

    if(statisticsType.equals(Statistics.ALL))
    {
      count = conversations.size();
    }
    else if(statisticsType.equals(Statistics.TODAY))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_WEEK))
    {
      for(Conversation conversation: conversations)
      {
        if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()))
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_MONTH))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == LocalDateTime.now().getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_3_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_6_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(3).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(4).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(5).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_9_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(3).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(4).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(5).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(6).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(7).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(8).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_YEAR))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getYear() == LocalDateTime.now().getYear())
        {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Get agent - customer conversations statistics for given statistics type
   * For specific user
   **/
  private int getAgentCustomerConversationsStatistics(String statisticsType, User user)
  {
    int count = 0;

    List<Conversation> conversations = conversationRepository.findByUser(user);

    if(statisticsType.equals(Statistics.ALL))
    {
      count = conversations.size();
    }
    else if(statisticsType.equals(Statistics.TODAY))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_WEEK))
    {
      for(Conversation conversation: conversations)
      {
        if(getCurrentWeekOfYear() == getWeekOfYear(conversation.getConversationDate()))
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_MONTH))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == LocalDateTime.now().getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_3_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_6_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(3).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(4).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(5).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_9_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getMonth() == now.getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(1).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(2).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(3).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(4).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(5).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(6).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(7).getMonth() ||
            conversation.getConversationDate().getMonth() == now.minusMonths(8).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_YEAR))
    {
      for(Conversation conversation: conversations)
      {
        if(conversation.getConversationDate().getYear() == LocalDateTime.now().getYear())
        {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Get agent - admin conversations statistics for given statistics type
   **/
  private int getAgentAdminConversationsStatistics(String statisticsType)
  {
    int count = 0;

    List<UserMessage> userMessages = userMessageRepository.findAll();

    if(statisticsType.equals(Statistics.ALL))
    {
      count = userMessages.size();
    }
    else if(statisticsType.equals(Statistics.TODAY))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_WEEK))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()))
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_MONTH))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == LocalDateTime.now().getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_3_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_6_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(3).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(4).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(5).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_9_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(3).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(4).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(5).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(6).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(7).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(8).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_YEAR))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getYear() == LocalDateTime.now().getYear())
        {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Get agent - admin conversations statistics for given statistics type
   * For specific user
   **/
  private int getAgentAdminConversationsStatistics(String statisticsType, User user)
  {
    int count = 0;

    List<UserMessage> userMessages = userMessageRepository.findByFromUserOrToUser(user, user);

    if(statisticsType.equals(Statistics.ALL))
    {
      count = userMessages.size();
    }
    else if(statisticsType.equals(Statistics.TODAY))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_WEEK))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(getCurrentWeekOfYear() == getWeekOfYear(userMessage.getSendDate()))
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_MONTH))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == LocalDateTime.now().getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_3_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_6_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(3).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(4).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(5).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.LAST_9_MONTH))
    {
      LocalDateTime now = LocalDateTime.now();

      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getMonth() == now.getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(1).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(2).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(3).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(4).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(5).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(6).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(7).getMonth() ||
            userMessage.getSendDate().getMonth() == now.minusMonths(8).getMonth())
        {
          count++;
        }
      }
    }
    else if(statisticsType.equals(Statistics.THIS_YEAR))
    {
      for(UserMessage userMessage: userMessages)
      {
        if(userMessage.getSendDate().getYear() == LocalDateTime.now().getYear())
        {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Get tickets statistics for given statistics type
   **/
  private int getTicketsStatistics(String statisticsType)
  {
    int count = 0;

    if(statisticsType.equals(Statistics.ALL))
    {
      count = ticketRepository.findAll().size();
    }
    else if(statisticsType.equals(Statistics.OPENED_TICKETS))
    {
      count = ticketRepository.findByAssigneeIsNullAndClosedByIsNull().size();
    }
    else if(statisticsType.equals(Statistics.ASSIGNED_TICKETS))
    {
      count = ticketRepository.findByAssigneeIsNotNullAndClosedByIsNull().size();
    }
    else if(statisticsType.equals(Statistics.CLOSED_TICKETS))
    {
      count = ticketRepository.findByClosedByIsNotNull().size();
    }

    return count;
  }

  /**
   * Get tickets statistics for given statistics type
   * For specific user
   **/
  private int getTicketsStatistics(String statisticsType, User user)
  {
    int count = 0;

    if(statisticsType.equals(Statistics.ALL))
    {
      count = ticketRepository.findByOpenedBy(user.getUserID()).size();
    }
    else if(statisticsType.equals(Statistics.OPENED_TICKETS))
    {
      count = ticketRepository.findByOpenedByAndClosedByIsNull(user.getUserID()).size();
    }
    else if(statisticsType.equals(Statistics.ASSIGNED_TICKETS))
    {
      count = ticketRepository.findByOpenedByAndAssigneeIsNotNullAndClosedByIsNull(user.getUserID()).size();
    }
    else if(statisticsType.equals(Statistics.CLOSED_TICKETS))
    {
      count = ticketRepository.findByOpenedByAndClosedByIsNotNull(user.getUserID()).size();
    }

    return count;
  }

  /**
   * Get users statistics for given statistics type
   **/
  private int getUsersStatistics(String statisticsType)
  {
    int count = 0;

    if(statisticsType.equals(Statistics.ALL))
    {
      count = userRepository.findAll().size();
    }
    else if(statisticsType.equals(Statistics.AGENTS))
    {
      count = userRepository.findByUserRole(UserRole.AGENT).size();
    }
    else if(statisticsType.equals(Statistics.ADMINS))
    {
      count = userRepository.findByUserRole(UserRole.ADMIN).size();
    }

    return count;
  }

  /**
   * This method calls above private functions for calculations of statistics
   **/
  public int getStatisticsFor(String statisticsType, String forStatistics)
  {
    if(forStatistics.equals(Statistics.FOR_AGENT_CUSTOMER_CONV))
    {
      return getAgentCustomerConversationsStatistics(statisticsType);
    }
    else if(forStatistics.equals(Statistics.FOR_AGENT_ADMIN_CONV))
    {
      return getAgentAdminConversationsStatistics(statisticsType);
    }
    else if(forStatistics.equals(Statistics.FOR_TICKETS))
    {
      return getTicketsStatistics(statisticsType);
    }
    else if(forStatistics.equals(Statistics.FOR_USERS))
    {
      return getUsersStatistics(statisticsType);
    }

    return 0;
  }

  /**
   * This method calls above private functions for calculations of statistics
   * For specific user
   **/
  public int getStatisticsFor(String statisticsType, String forStatistics, User user)
  {
    if(forStatistics.equals(Statistics.FOR_AGENT_CUSTOMER_CONV))
    {
      return getAgentCustomerConversationsStatistics(statisticsType, user);
    }
    else if(forStatistics.equals(Statistics.FOR_AGENT_ADMIN_CONV))
    {
      return getAgentAdminConversationsStatistics(statisticsType, user);
    }
    else if(forStatistics.equals(Statistics.FOR_TICKETS))
    {
      return getTicketsStatistics(statisticsType, user);
    }
    else if(forStatistics.equals(Statistics.FOR_USERS))
    {
      return getUsersStatistics(statisticsType);
    }

    return 0;
  }
}
