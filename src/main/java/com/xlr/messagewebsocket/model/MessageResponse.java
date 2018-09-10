package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents response message that is being forwarded to clients
 **/
public class MessageResponse
{
  private String responseMessage;
  private String theID;
  private String theName;
  private String conversationID;

  public MessageResponse()
  {
  }

  public MessageResponse(String responseMessage, String theID, String theName, String conversationID)
  {
    this.responseMessage = responseMessage;
    this.theID = theID;
    this.theName = theName;
    this.conversationID = conversationID;
  }

  @JsonProperty("content")
  public String getResponseMessage()
  {
    return responseMessage;
  }

  public void setResponseMessage(String responseMessage)
  {
    this.responseMessage = responseMessage;
  }

  public String getTheID()
  {
    return theID;
  }

  public void setTheID(String theID)
  {
    this.theID = theID;
  }

  public String getTheName()
  {
    return theName;
  }

  public void setTheName(String theName)
  {
    this.theName = theName;
  }

  public String getConversationID()
  {
    return conversationID;
  }

  public void setConversationID(String conversationID)
  {
    this.conversationID = conversationID;
  }
}
