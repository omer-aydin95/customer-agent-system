package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.ConversationKeyword;
import com.xlr.messagewebsocket.constant.Flag;
import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.constant.UserStatus;
import com.xlr.messagewebsocket.model.*;
import com.xlr.messagewebsocket.resource.ConversationRepository;
import com.xlr.messagewebsocket.resource.EncryptionService;
import com.xlr.messagewebsocket.resource.UserRepository;
import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Incoming and outgoing messages to websocket are being controlled here
 * And the controller determines which direction the message should be forwarded
 *
 * Also HTTP request endpoint controller
 **/
@Controller
public class MessageController
{
  private SimpMessagingTemplate template;
  private UserRepository userRepository;
  private ConversationRepository conversationRepository;
  private OutRestController outRestController;

  private volatile String isAccepted;
  private volatile User startConversationWith;

  public MessageController(SimpMessagingTemplate template, UserRepository userRepository,
                           ConversationRepository conversationRepository, OutRestController outRestController)
  {
    this.template = template;
    this.userRepository = userRepository;
    this.conversationRepository = conversationRepository;
    this.outRestController = outRestController;

    isAccepted = null;
    startConversationWith = null;
  }

  /**
   * Message forwarding and conversation establishing or closing operations
   **/
  @MessageMapping("/message")
  public void forwardMessage(Message message, GetAgentRequest getAgentRequest) throws IOException
  {
    // Connection and conversation establishing, finding available agent
    if(message.getMessageContent().equals(ConversationKeyword.CONVERSATION_ESTABLISH))
    {
      Conversation isThereAnyConv = conversationRepository.findByConversationIDAndIsContinue(message.getTheConversationID(),
          Flag.CONVERSATION_CONTINUE);

      if(isThereAnyConv == null)
      {
        List<User> availableAgents = userRepository.findByUserStatus(UserStatus.AVAILABLE);

        /*
         * If there is any available agent, pick one of them
         * And send conversation request notification to him/her
         */
        if(availableAgents.size() > 0)
        {
          int randomAgent = new Random().nextInt(availableAgents.size());
          User agent = availableAgents.get(randomAgent);
          String conversationID = EncryptionService.getUniqueID();

          Conversation conversation = new Conversation(new Long(0), LocalDateTime.now(), conversationID,
                  Flag.CONVERSATION_CREATED, Flag.CONVERSATION_CREATED, getAgentRequest.getSourceType(),
                  getAgentRequest.getMessageChannelID(), getAgentRequest.getSourceReceiverID(), getAgentRequest.getChannelUserID(),
                  getAgentRequest.getConversationID(), agent);
          conversationRepository.save(conversation);

          MessageList messageList = new MessageList();
          messageList.setToken(outRestController.getTokenFromAPI().getToken());

          MessageInOut messageInOut = new MessageInOut();
          messageInOut.setID(EncryptionService.getUniqueID());
          messageInOut.setSourceType(getAgentRequest.getSourceType());
          messageInOut.setMessageChannelID(getAgentRequest.getMessageChannelID());
          messageInOut.setSourceReceiverID(getAgentRequest.getSourceReceiverID());
          messageInOut.setChannelUserID(getAgentRequest.getChannelUserID());
          messageInOut.setMessageContent("Please be patient, we are searching an available agent.");
          messageInOut.setConversationID(getAgentRequest.getConversationID());

          messageList.getMessageList().add(messageInOut);

          outRestController.sendMessageToAPI(messageList);

          template.convertAndSend("/agent/" + agent.getUserID() + "/message",
              new MessageResponse(ConversationKeyword.CONVERSATION_REQUEST, message.getTheID(),
                  message.getTheName(), conversationID));
        }
        else
        {
          MessageList messageList = new MessageList();
          messageList.setToken(outRestController.getTokenFromAPI().getToken());

          MessageInOut messageInOut = new MessageInOut();
          messageInOut.setID(EncryptionService.getUniqueID());
          messageInOut.setSourceType(getAgentRequest.getSourceType());
          messageInOut.setMessageChannelID(getAgentRequest.getMessageChannelID());
          messageInOut.setSourceReceiverID(getAgentRequest.getSourceReceiverID());
          messageInOut.setChannelUserID(getAgentRequest.getChannelUserID());
          messageInOut.setMessageContent("We are sorry, there is not any available agent. Please try again after 5 minutes.");
          messageInOut.setConversationID(getAgentRequest.getConversationID());

          messageList.getMessageList().add(messageInOut);

          outRestController.sendMessageToAPI(messageList);
        }
      }
    }
    /*
     * If customer or agent finishes the conversation
     * Close conversation
     * Calculate conversation duration and update database
     * Then send conversation close notification customer and agent
     * Also update agent availability status to AVAILABLE
     */
    else if(message.getMessageContent().equals(ConversationKeyword.CONVERSATION_CLOSED))
    {
      Conversation conversation = conversationRepository.findByConversationIDAndIsContinue(message.getTheConversationID(),
              Flag.CONVERSATION_CONTINUE);

      if(conversation != null)
      {
        conversation.getUser().setUserStatus(UserStatus.AVAILABLE);
        userRepository.save(conversation.getUser());

        LocalDateTime now = LocalDateTime.now();

        long duration = ((now.getMinute() - conversation.getConversationDate().getMinute()) * 60)
            + (now.getSecond() - conversation.getConversationDate().getSecond());

        conversation.setDuration(duration);
        conversation.setIsContinue(Flag.CONVERSATION_END);
        conversationRepository.save(conversation);

        MessageList messageList = new MessageList();
        messageList.setToken(outRestController.getTokenFromAPI().getToken());

        MessageInOut messageInOut = new MessageInOut();
        messageInOut.setID(EncryptionService.getUniqueID());
        messageInOut.setSourceType(conversation.getSourceType());
        messageInOut.setMessageChannelID(conversation.getChannelID());
        messageInOut.setSourceReceiverID(conversation.getSourceReceiverID());
        messageInOut.setChannelUserID(conversation.getChannelUserID());
        messageInOut.setMessageContent("The agent has been disconnected.");
        messageInOut.setConversationID(conversation.getConversationID());

        messageList.getMessageList().add(messageInOut);

        outRestController.sendMessageToAPI(messageList);

        template.convertAndSend("/agent/" + conversation.getUser().getUserID() + "/message",
                new MessageResponse("The customer has been disconnected.", message.getTheID(),
                        message.getTheName(), conversation.getConversationID()));
      }
    }
    /*
     * If the agent accepts the conversation request
     * Send conversation start notification to customer
     * And insert conversation details to database
     * Also update agent availability status to BUSY
     */
    else if(message.getMessageContent().equals(ConversationKeyword.CONVERSATION_ACCEPT))
    {
      Conversation conversation = conversationRepository.findByConversationIDAndIsContinue(message.getTheConversationID(),
              Flag.CONVERSATION_CREATED);

      isAccepted = Flag.CONVERSATION_ACCEPTED;
      startConversationWith = conversation.getUser();

      conversation.setIsContinue(Flag.CONVERSATION_CONTINUE);
      conversation.setConversationStatus(Flag.CONVERSATION_ACCEPTED);
      conversationRepository.save(conversation);

      conversation.getUser().setUserStatus(UserStatus.BUSY);
      userRepository.save(conversation.getUser());

      MessageList messageList = new MessageList();
      messageList.setToken(outRestController.getTokenFromAPI().getToken());

      MessageInOut messageInOut = new MessageInOut();
      messageInOut.setID(EncryptionService.getUniqueID());
      messageInOut.setSourceType(conversation.getSourceType());
      messageInOut.setMessageChannelID(conversation.getChannelID());
      messageInOut.setSourceReceiverID(conversation.getSourceReceiverID());
      messageInOut.setChannelUserID(conversation.getChannelUserID());
      messageInOut.setMessageContent("The conversation has been started.");
      messageInOut.setConversationID(conversation.getConversationID());

      messageList.getMessageList().add(messageInOut);

      outRestController.sendMessageToAPI(messageList);

      template.convertAndSend("/agent/" + conversation.getCustomerID() + "/message",
          new MessageResponse("The conversation has been started.",
              conversation.getCustomerID(), conversation.getCustomerName(), conversation.getConversationID()));
    }
    // If agent ignores conversation request, send notification to customer
    else if(message.getMessageContent().equals(ConversationKeyword.CONVERSATION_IGNORE))
    {
      Conversation conversation = conversationRepository.findByConversationIDAndIsContinue(message.getTheConversationID(),
              Flag.CONVERSATION_CREATED);

      isAccepted = Flag.CONVERSATION_IGNORED;
      startConversationWith = null;

      conversation.setIsContinue(Flag.CONVERSATION_END);
      conversation.setConversationStatus(Flag.CONVERSATION_IGNORED);
      conversationRepository.save(conversation);

      MessageList messageList = new MessageList();
      messageList.setToken(outRestController.getTokenFromAPI().getToken());

      MessageInOut messageInOut = new MessageInOut();
      messageInOut.setID(EncryptionService.getUniqueID());
      messageInOut.setSourceType(conversation.getSourceType());
      messageInOut.setMessageChannelID(conversation.getChannelID());
      messageInOut.setSourceReceiverID(conversation.getSourceReceiverID());
      messageInOut.setChannelUserID(conversation.getChannelUserID());
      messageInOut.setMessageContent("We are sorry, there is not any available agent. Please try again after 5 minutes.");
      messageInOut.setConversationID(conversation.getConversationID());

      messageList.getMessageList().add(messageInOut);

      outRestController.sendMessageToAPI(messageList);

      template.convertAndSend("/agent/" + message.getTheID() + "/message",
          new MessageResponse(ConversationKeyword.CONVERSATION_CANCELLED,
              null, null, null));
    }
    /*
     * Customer puts C to beginning of the message then sends
     * Agent puts A to beginning of the message then sends
     * In this way the controller determines which direction the message should be forwarded
     */
    else if(message.getMessageContent().charAt(0) == Flag.CUSTOMER_FLAG)
    {
      Conversation conversation = conversationRepository.findByCustomerIDAndIsContinue(message.getTheID(),
          Flag.CONVERSATION_CONTINUE);

      template.convertAndSend("/agent/" + conversation.getUser().getUserID() + "/message",
          new MessageResponse(message.getMessageContent().substring(1), message.getTheID(),
              message.getTheName(), conversation.getConversationID()));
    }
    else if(message.getMessageContent().charAt(0) == Flag.AGENT_FLAG)
    {
      Conversation conversation = conversationRepository.findByConversationIDAndIsContinue(message.getTheConversationID(),
              Flag.CONVERSATION_CONTINUE);

      MessageList messageList = new MessageList();
      messageList.setToken(outRestController.getTokenFromAPI().getToken());

      MessageInOut messageInOut = new MessageInOut();
      messageInOut.setID(EncryptionService.getUniqueID());
      messageInOut.setSourceType(conversation.getSourceType());
      messageInOut.setMessageChannelID(conversation.getChannelID());
      messageInOut.setSourceReceiverID(conversation.getSourceReceiverID());
      messageInOut.setChannelUserID(conversation.getChannelUserID());
      messageInOut.setMessageContent(message.getMessageContent().substring(1));
      messageInOut.setConversationID(conversation.getConversationID());

      messageList.getMessageList().add(messageInOut);

      outRestController.sendMessageToAPI(messageList);
    }
  }

  /**
   * Use in order to send message from external endpoint
   * It will forward to message to agent
   *
   * HTTP POST Request
   **/
  @PostMapping("/send-message")
  public ResponseEntity postSendMessage(@RequestBody MessageList messageList) throws IOException
  {
    for(MessageInOut messageInOut: messageList.getMessageList())
    {
      Conversation conversation = conversationRepository.findByChannelIDAndSourceReceiverIDAndSourceTypeAndChannelUserIDAndCnvrstIDAndIsContinue(
              messageInOut.getMessageChannelID(), messageInOut.getSourceReceiverID(), messageInOut.getSourceType(),
              messageInOut.getChannelUserID(), messageInOut.getConversationID(), Flag.CONVERSATION_CONTINUE
      );

      Message message;

      if(!messageInOut.getMessageContent().equals(ConversationKeyword.CONVERSATION_CLOSED))
      {
        message = new Message(Flag.CUSTOMER_FLAG + messageInOut.getMessageContent(),
                null, null, conversation.getConversationID());
      }
      else
      {
        message = new Message(messageInOut.getMessageContent(),
                null, null, conversation.getConversationID());
      }

      forwardMessage(message, null);
    }

    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * Use in order to request available agent
   * It will return available agents list and will establish connection with one of them
   *
   * HTTP POST Request
   **/
  @PostMapping("/get-agent")
  @ResponseBody
  public ResponseEntity postGetAgent(@RequestBody GetAgentRequest getAgentRequest) throws IOException
  {
    if(getAgentRequest.getSourceType() == null || getAgentRequest.getSourceReceiverID() == null || getAgentRequest.getMessageChannelID() == null ||
        getAgentRequest.getConversationID() == null || getAgentRequest.getChannelUserID() == null)
    {
      HttpBadRequest getAgentBadRequest = new HttpBadRequest(
              "Please specify all of ST, MSGCHID, SRCRCVID, CHUSRID, CNVRSTNID values in an json object."
      );

      return new ResponseEntity(getAgentBadRequest, HttpStatus.BAD_REQUEST);
    }

    GetAgentResponse getAgentResponse = new GetAgentResponse();

    List<User> users = userRepository.findByUserRole(UserRole.AGENT);
    getAgentResponse.setTotalAgents(users.size());

    users = userRepository.findByUserRoleAndUserStatus(UserRole.AGENT, UserStatus.AVAILABLE);
    getAgentResponse.setAvailableAgents(users.size());

    if(users.size() > 0)
    {
      getAgentResponse.setAvailableAgentsList(users);

      Message message = new Message(ConversationKeyword.CONVERSATION_ESTABLISH, null, null, null);

      forwardMessage(message, getAgentRequest);

      // Wait for agent acceptance
      while(isAccepted == null)
      { }

      if(isAccepted.equals(Flag.CONVERSATION_ACCEPTED))
      {
        getAgentResponse.setStartConversationWith(startConversationWith);
      }
    }

    users = userRepository.findByUserRoleAndUserStatus(UserRole.AGENT, UserStatus.BUSY);
    getAgentResponse.setBusyAgents(users.size());

    users = userRepository.findByUserRoleAndUserStatus(UserRole.AGENT, UserStatus.LOGOUT);
    getAgentResponse.setOfflineAgents(users.size());

    isAccepted = null;
    startConversationWith = null;

    return new ResponseEntity(getAgentResponse, HttpStatus.OK);
  }
}
