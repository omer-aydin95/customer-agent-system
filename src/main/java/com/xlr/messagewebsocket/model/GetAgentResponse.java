package com.xlr.messagewebsocket.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents response of the agent request
 *
 * Response contains available agents list
 **/
public class GetAgentResponse
{
  private int totalAgents;

  private int availableAgents;

  private int busyAgents;

  private int offlineAgents;

  private List<User> availableAgentsList;

  private User startConversationWith;

  public GetAgentResponse()
  {
    availableAgentsList = new ArrayList<>();
  }

  public GetAgentResponse(int totalAgents, int availableAgents, int busyAgents,
                          int offlineAgents, List<User> availableAgentsList, User startConversationWith)
  {
    this.totalAgents = totalAgents;
    this.availableAgents = availableAgents;
    this.busyAgents = busyAgents;
    this.offlineAgents = offlineAgents;
    this.availableAgentsList = availableAgentsList;
    this.startConversationWith = startConversationWith;
  }

  public int getTotalAgents()
  {
    return totalAgents;
  }

  public void setTotalAgents(int totalAgents)
  {
    this.totalAgents = totalAgents;
  }

  public int getAvailableAgents()
  {
    return availableAgents;
  }

  public void setAvailableAgents(int availableAgents)
  {
    this.availableAgents = availableAgents;
  }

  public int getBusyAgents()
  {
    return busyAgents;
  }

  public void setBusyAgents(int busyAgents)
  {
    this.busyAgents = busyAgents;
  }

  public int getOfflineAgents()
  {
    return offlineAgents;
  }

  public void setOfflineAgents(int offlineAgents)
  {
    this.offlineAgents = offlineAgents;
  }

  public List<User> getAvailableAgentsList()
  {
    return availableAgentsList;
  }

  public void setAvailableAgentsList(List<User> availableAgentsList)
  {
    this.availableAgentsList = availableAgentsList;
  }

  public User getStartConversationWith()
  {
    return startConversationWith;
  }

  public void setStartConversationWith(User startConversationWith)
  {
    this.startConversationWith = startConversationWith;
  }
}
