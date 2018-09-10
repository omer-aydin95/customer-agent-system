package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents messages that sent to external API
 * Also contains list of MessageInOut
 **/
public class MessageList
{
  @JsonProperty("TOKEN")
  private String token;

  @JsonProperty("MSG_LIST")
  private List<MessageInOut> messageList;

  public MessageList()
  {
    messageList = new ArrayList<>();
  }

  public MessageList(String token, List<MessageInOut> messageList)
  {
    this.token = token;
    this.messageList = messageList;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public List<MessageInOut> getMessageList()
  {
    return messageList;
  }

  public void setMessageList(List<MessageInOut> messageList)
  {
    this.messageList = messageList;
  }
}
