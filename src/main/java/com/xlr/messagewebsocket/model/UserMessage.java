package com.xlr.messagewebsocket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents messaging between agents and admins
 **/
@Entity
@Table(name = "user_messages")
public class UserMessage
{
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "message_content", nullable = false)
  private String messageContent;

  @Column(name = "send_date", nullable = false)
  private LocalDateTime sendDate;

  @ManyToOne(targetEntity = User.class)
  private User fromUser;

  @ManyToOne(targetEntity = User.class)
  private User toUser;

  @Column(name = "is_read", nullable = false)
  private String isRead;

  public UserMessage()
  {
  }

  public UserMessage(String messageContent, LocalDateTime sendDate, User fromUser,
                     User toUser, String isRead)
  {
    this.messageContent = messageContent;
    this.sendDate = sendDate;
    this.fromUser = fromUser;
    this.toUser = toUser;
    this.isRead = isRead;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getMessageContent()
  {
    return messageContent;
  }

  public void setMessageContent(String messageContent)
  {
    this.messageContent = messageContent;
  }

  public LocalDateTime getSendDate()
  {
    return sendDate;
  }

  public void setSendDate(LocalDateTime sendDate)
  {
    this.sendDate = sendDate;
  }

  public User getFromUser()
  {
    return fromUser;
  }

  public void setFromUser(User fromUser)
  {
    this.fromUser = fromUser;
  }

  public User getToUser()
  {
    return toUser;
  }

  public void setToUser(User toUser)
  {
    this.toUser = toUser;
  }

  public String getIsRead()
  {
    return isRead;
  }

  public void setIsRead(String isRead)
  {
    this.isRead = isRead;
  }
}
