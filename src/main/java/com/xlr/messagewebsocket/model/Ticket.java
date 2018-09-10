package com.xlr.messagewebsocket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents issue tickets that is being opened by agents or admins
 *
 * Issue tickets is being solved by admins
 **/
@Entity
@Table(name = "tickets")
public class Ticket
{
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "issue_type", nullable = false)
  private String issueType;

  @Column(name = "opened_by", nullable = false)
  private String openedBy;

  @Column(name = "issue_detail")
  private String issueDetail;

  @ManyToOne(targetEntity = User.class)
  private User assignee;

  @ManyToOne(targetEntity = User.class)
  private User closedBy;

  @Column(name = "issue_status", nullable = false)
  private String issueStatus;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "assigned_at")
  private LocalDateTime assignedAt;

  @Column(name = "closed_at")
  private LocalDateTime closedAt;

  public Ticket()
  {
  }

  public Ticket(String issueType, String openedBy, String issueStatus, LocalDateTime createdAt)
  {
    this.issueType = issueType;
    this.openedBy = openedBy;
    this.issueStatus = issueStatus;
    this.createdAt = createdAt;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getIssueType()
  {
    return issueType;
  }

  public void setIssueType(String issueType)
  {
    this.issueType = issueType;
  }

  public String getOpenedBy()
  {
    return openedBy;
  }

  public void setOpenedBy(String openedBy)
  {
    this.openedBy = openedBy;
  }

  public String getIssueDetail()
  {
    return issueDetail;
  }

  public void setIssueDetail(String issueDetail)
  {
    this.issueDetail = issueDetail;
  }

  public User getAssignee()
  {
    return assignee;
  }

  public void setAssignee(User assignee)
  {
    this.assignee = assignee;
  }

  public User getClosedBy()
  {
    return closedBy;
  }

  public void setClosedBy(User closedBy)
  {
    this.closedBy = closedBy;
  }

  public String getIssueStatus()
  {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus)
  {
    this.issueStatus = issueStatus;
  }

  public LocalDateTime getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt)
  {
    this.createdAt = createdAt;
  }

  public LocalDateTime getAssignedAt()
  {
    return assignedAt;
  }

  public void setAssignedAt(LocalDateTime assignedAt)
  {
    this.assignedAt = assignedAt;
  }

  public LocalDateTime getClosedAt()
  {
    return closedAt;
  }

  public void setClosedAt(LocalDateTime closedAt)
  {
    this.closedAt = closedAt;
  }
}
