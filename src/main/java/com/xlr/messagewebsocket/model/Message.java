package com.xlr.messagewebsocket.model;

/**
 * Represents message that is being taken from websocket
 **/
public class Message
{
  private String messageContent;
  private String theID;
  private String theName;
  private String theConversationID;

  public Message()
  {
  }

  public Message(String messageContent, String theID, String theName, String theConversationID)
  {
    this.messageContent = messageContent;
    this.theID = theID;
    this.theName = theName;
    this.theConversationID = theConversationID;
  }

  public String getMessageContent()
  {
    return messageContent;
  }

  public String getTheID()
  {
    return theID;
  }

  public String getTheName()
  {
    return theName;
  }

  public void setMessageContent(String messageContent)
  {
    this.messageContent = messageContent;
  }

  public void setTheID(String theID)
  {
    this.theID = theID;
  }

  public void setTheName(String theName)
  {
    this.theName = theName;
  }

  public String getTheConversationID()
  {
    return theConversationID;
  }

  public void setTheConversationID(String theConversationID)
  {
    this.theConversationID = theConversationID;
  }
}
