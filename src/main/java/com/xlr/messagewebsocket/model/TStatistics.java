package com.xlr.messagewebsocket.model;

import com.xlr.messagewebsocket.resource.IStatisticsObject;

/**
 * Represents ticket statistics
 * In terms of open, assign and close count
 **/
public class TStatistics implements IStatisticsObject
{
  private int opened;
  private int assigned;
  private int closed;

  public TStatistics()
  {
    opened = 0;
    assigned = 0;
    closed = 0;
  }

  public TStatistics(int opened, int assigned, int closed)
  {
    this.opened = opened;
    this.assigned = assigned;
    this.closed = closed;
  }

  public int getOpened()
  {
    return opened;
  }

  public void setOpened(int opened)
  {
    this.opened = opened;
  }

  public int getAssigned()
  {
    return assigned;
  }

  public void setAssigned(int assigned)
  {
    this.assigned = assigned;
  }

  public int getClosed()
  {
    return closed;
  }

  public void setClosed(int closed)
  {
    this.closed = closed;
  }
}
