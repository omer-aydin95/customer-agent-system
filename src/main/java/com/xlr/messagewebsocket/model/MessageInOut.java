package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents messages that sent to external API
 **/
public class MessageInOut
{
  @JsonProperty("ID")
  private String ID;

  @JsonProperty("ST")
  private String sourceType;

  @JsonProperty("MSGTYP")
  private String messageType;

  @JsonProperty("SUBTYP")
  private String subType;

  @JsonProperty("MSGCHID")
  private String messageChannelID;

  @JsonProperty("SRCRCVID")
  private String sourceReceiverID;

  @JsonProperty("CHUSRID")
  private String channelUserID;

  @JsonProperty("CHUSRNAME")
  private String channelUserName;

  @JsonProperty("CHANNELDATA")
  private String channelData;

  @JsonProperty("MSGC")
  private String messageContent;

  @JsonProperty("OP")
  private String operatorName;

  @JsonProperty("REMREF")
  private String remoteRef;

  @JsonProperty("LAT")
  private String latitude;

  @JsonProperty("LNG")
  private String longitude;

  @JsonProperty("CNVRSTNID")
  private String conversationID;

  @JsonProperty("ERRDSC")
  private String errorDescription;

  @JsonProperty("CREATEDATE")
  private LocalDateTime createDate;

  @JsonProperty("ATTCHMNTS")
  private List<MessageAttachment> attachments;

  public MessageInOut()
  {
  }

  public MessageInOut(String ID, String sourceType, String messageType,
                      String subType, String messageChannelID, String sourceReceiverID,
                      String channelUserID, String channelUserName, String channelData,
                      String messageContent, String operatorName, String remoteRef,
                      String latitude, String longitude, String conversationID,
                      String errorDescription, LocalDateTime createDate, List<MessageAttachment> attachments)
  {
    this.ID = ID;
    this.sourceType = sourceType;
    this.messageType = messageType;
    this.subType = subType;
    this.messageChannelID = messageChannelID;
    this.sourceReceiverID = sourceReceiverID;
    this.channelUserID = channelUserID;
    this.channelUserName = channelUserName;
    this.channelData = channelData;
    this.messageContent = messageContent;
    this.operatorName = operatorName;
    this.remoteRef = remoteRef;
    this.latitude = latitude;
    this.longitude = longitude;
    this.conversationID = conversationID;
    this.errorDescription = errorDescription;
    this.createDate = createDate;
    this.attachments = attachments;
  }

  public String getID()
  {
    return ID;
  }

  public void setID(String ID)
  {
    this.ID = ID;
  }

  public String getSourceType()
  {
    return sourceType;
  }

  public void setSourceType(String sourceType)
  {
    this.sourceType = sourceType;
  }

  public String getMessageType()
  {
    return messageType;
  }

  public void setMessageType(String messageType)
  {
    this.messageType = messageType;
  }

  public String getSubType()
  {
    return subType;
  }

  public void setSubType(String subType)
  {
    this.subType = subType;
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

  public String getChannelUserName()
  {
    return channelUserName;
  }

  public void setChannelUserName(String channelUserName)
  {
    this.channelUserName = channelUserName;
  }

  public String getChannelData()
  {
    return channelData;
  }

  public void setChannelData(String channelData)
  {
    this.channelData = channelData;
  }

  public String getMessageContent()
  {
    return messageContent;
  }

  public void setMessageContent(String messageContent)
  {
    this.messageContent = messageContent;
  }

  public String getOperatorName()
  {
    return operatorName;
  }

  public void setOperatorName(String operatorName)
  {
    this.operatorName = operatorName;
  }

  public String getRemoteRef()
  {
    return remoteRef;
  }

  public void setRemoteRef(String remoteRef)
  {
    this.remoteRef = remoteRef;
  }

  public String getLatitude()
  {
    return latitude;
  }

  public void setLatitude(String latitude)
  {
    this.latitude = latitude;
  }

  public String getLongitude()
  {
    return longitude;
  }

  public void setLongitude(String longitude)
  {
    this.longitude = longitude;
  }

  public String getConversationID()
  {
    return conversationID;
  }

  public void setConversationID(String conversationID)
  {
    this.conversationID = conversationID;
  }

  public String getErrorDescription()
  {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription)
  {
    this.errorDescription = errorDescription;
  }

  public LocalDateTime getCreateDate()
  {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate)
  {
    this.createDate = createDate;
  }

  public List<MessageAttachment> getAttachments()
  {
    return attachments;
  }

  public void setAttachments(List<MessageAttachment> attachments)
  {
    this.attachments = attachments;
  }
}
