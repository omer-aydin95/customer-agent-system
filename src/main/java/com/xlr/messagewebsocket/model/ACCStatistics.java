package com.xlr.messagewebsocket.model;

import com.xlr.messagewebsocket.resource.IStatisticsObject;

/**
 * Represents agent - customer conversations statistics
 * In terms of accepted and ignored count
 **/
public class ACCStatistics implements IStatisticsObject
{
  private int accepted;
  private int ignored;

  public ACCStatistics()
  {
    accepted = 0;
    ignored = 0;
  }

  public ACCStatistics(int accepted, int ignored)
  {
    this.accepted = accepted;
    this.ignored = ignored;
  }

  public int getAccepted()
  {
    return accepted;
  }

  public void setAccepted(int accepted)
  {
    this.accepted = accepted;
  }

  public int getIgnored()
  {
    return ignored;
  }

  public void setIgnored(int ignored)
  {
    this.ignored = ignored;
  }
}
