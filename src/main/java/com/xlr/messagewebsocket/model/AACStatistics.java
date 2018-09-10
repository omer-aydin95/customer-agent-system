package com.xlr.messagewebsocket.model;

import com.xlr.messagewebsocket.resource.IStatisticsObject;

/**
 * Represents agent - admin conversations statistics
 * In terms of sent message count
 **/
public class AACStatistics implements IStatisticsObject
{
  private int messages;

  public AACStatistics()
  {
    messages = 0;
  }

  public AACStatistics(int messages)
  {
    this.messages = messages;
  }

  public int getMessages()
  {
    return messages;
  }

  public void setMessages(int messages)
  {
    this.messages = messages;
  }
}
