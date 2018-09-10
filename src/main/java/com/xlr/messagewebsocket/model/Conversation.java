package com.xlr.messagewebsocket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents conversations between agent and customer
 **/
@Entity
@Table(name = "conversations")
public class Conversation
{
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "customer_name")
  private String customerName;

  @Column(name = "customer_id")
  private String customerID;

  @Column(name = "duration", nullable = false)
  private Long duration;

  @Column(name = "conversation_date", nullable = false)
  private LocalDateTime conversationDate;

  @Column(name = "conversation_id", unique = true, nullable = false)
  private String conversationID;

  @Column(name = "conversation_status", nullable = false)
  private String conversationStatus;

  @Column(name = "is_continue", nullable = false)
  private String isContinue;

  @Column(name = "source_type", nullable = false)
  private String sourceType;

  @Column(name = "channel_id", nullable = false)
  private String channelID;

  @Column(name = "source_receiver_id", nullable = false)
  private String sourceReceiverID;

  @Column(name = "channel_user_id", nullable = false)
  private String channelUserID;

  @Column(name = "cnvrst_id", nullable = false)
  private String cnvrstID;

  @ManyToOne(targetEntity = User.class)
  private User user;

  public Conversation()
  {
  }

  public Conversation(Long duration, LocalDateTime conversationDate, String conversationID,
                      String conversationStatus, String isContinue, String sourceType,
                      String channelID, String sourceReceiverID, String channelUserID,
                      String cnvrstID, User user)
  {
    this.duration = duration;
    this.conversationDate = conversationDate;
    this.conversationID = conversationID;
    this.conversationStatus = conversationStatus;
    this.isContinue = isContinue;
    this.sourceType = sourceType;
    this.channelID = channelID;
    this.sourceReceiverID = sourceReceiverID;
    this.channelUserID = channelUserID;
    this.cnvrstID = cnvrstID;
    this.user = user;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getCustomerName()
  {
    return customerName;
  }

  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }

  public String getCustomerID()
  {
    return customerID;
  }

  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
  }

  public Long getDuration()
  {
    return duration;
  }

  public void setDuration(Long duration)
  {
    this.duration = duration;
  }

  public LocalDateTime getConversationDate()
  {
    return conversationDate;
  }

  public void setConversationDate(LocalDateTime conversationDate)
  {
    this.conversationDate = conversationDate;
  }

  public String getConversationID()
  {
    return conversationID;
  }

  public void setConversationID(String conversationID)
  {
    this.conversationID = conversationID;
  }

  public String getConversationStatus()
  {
    return conversationStatus;
  }

  public void setConversationStatus(String conversationStatus)
  {
    this.conversationStatus = conversationStatus;
  }

  public String getIsContinue()
  {
    return isContinue;
  }

  public void setIsContinue(String isContinue)
  {
    this.isContinue = isContinue;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getSourceType()
  {
    return sourceType;
  }

  public void setSourceType(String sourceType)
  {
    this.sourceType = sourceType;
  }

  public String getChannelID()
  {
    return channelID;
  }

  public void setChannelID(String channelID)
  {
    this.channelID = channelID;
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

  public String getCnvrstID()
  {
    return cnvrstID;
  }

  public void setCnvrstID(String cnvrstID)
  {
    this.cnvrstID = cnvrstID;
  }
}
