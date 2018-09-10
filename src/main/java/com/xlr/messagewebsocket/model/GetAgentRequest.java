package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents agent request object
 *
 * Request an agent via this object
 **/
public class GetAgentRequest
{
  @JsonProperty("ST")
  private String sourceType;

  @JsonProperty("MSGCHID")
  private String messageChannelID;

  @JsonProperty("SRCRCVID")
  private String sourceReceiverID;

  @JsonProperty("CHUSRID")
  private String channelUserID;

  @JsonProperty("CNVRSTNID")
  private String conversationID;

  public GetAgentRequest()
  {
  }

  public GetAgentRequest(String sourceType, String messageChannelID, String sourceReceiverID, String channelUserID, String conversationID)
  {
    this.sourceType = sourceType;
    this.messageChannelID = messageChannelID;
    this.sourceReceiverID = sourceReceiverID;
    this.channelUserID = channelUserID;
    this.conversationID = conversationID;
  }

  public String getSourceType()
  {
    return sourceType;
  }

  public void setSourceType(String sourceType)
  {
    this.sourceType = sourceType;
  }

  public String getMessageChannelID()
  {
    return messageChannelID;
  }

  public void setMessageChannelID(String messageChannelID)
  {
    this.messageChannelID = messageChannelID;
  }

  public String getSourceReceiverID()
  {
    return sourceReceiverID;
  }

  public void setSourceReceiverID(String sourceReceiverID)
  {
    this.sourceReceiverID = sourceReceiverID;
  }

  public String getChannelUserID()
  {
    return channelUserID;
  }

  public void setChannelUserID(String channelUserID)
  {
    this.channelUserID = channelUserID;
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
