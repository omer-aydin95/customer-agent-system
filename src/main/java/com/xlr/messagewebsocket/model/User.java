package com.xlr.messagewebsocket.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents system users (agents/admins)
 **/
@Entity
@Table(name = "users")
public class User
{
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id", unique = true, nullable = false)
  private String userID;

  @Column(name = "user_firstname", nullable = false)
  private String userFirstname;

  @Column(name = "user_lastname", nullable = false)
  private String userLastname;

  @Column(name = "user_status", nullable = false)
  private String userStatus;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "passwrd", nullable = false)
  private String password;

  @Column(name = "user_role", nullable = false)
  private String userRole;

  @Column(name = "user_email", unique = true, nullable = false)
  private String userEmail;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "gender", nullable = false)
  private String gender;

  @Column(name = "profile_picture", nullable = false)
  private String profilePicture;

  public User()
  {
  }

  public User(String userID, String userFirstname, String userLastname,
              String userStatus, String username, String password,
              String userRole, String userEmail, LocalDateTime createdAt,
              String gender, String profilePicture)
  {
    this.userID = userID;
    this.userFirstname = userFirstname;
    this.userLastname = userLastname;
    this.userStatus = userStatus;
    this.username = username;
    this.password = password;
    this.userRole = userRole;
    this.userEmail = userEmail;
    this.createdAt = createdAt;
    this.gender = gender;
    this.profilePicture = profilePicture;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getUserID()
  {
    return userID;
  }

  public void setUserID(String userID)
  {
    this.userID = userID;
  }

  public String getUserFirstname()
  {
    return userFirstname;
  }

  public void setUserFirstname(String userFirstname)
  {
    this.userFirstname = userFirstname;
  }

  public String getUserLastname()
  {
    return userLastname;
  }

  public void setUserLastname(String userLastname)
  {
    this.userLastname = userLastname;
  }

  public String getUserStatus()
  {
    return userStatus;
  }

  public void setUserStatus(String userStatus)
  {
    this.userStatus = userStatus;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getUserRole()
  {
    return userRole;
  }

  public void setUserRole(String userRole)
  {
    this.userRole = userRole;
  }

  public LocalDateTime getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt)
  {
    this.createdAt = createdAt;
  }

  public String getUserEmail()
  {
    return userEmail;
  }

  public void setUserEmail(String userEmail)
  {
    this.userEmail = userEmail;
  }

  public String getGender()
  {
    return gender;
  }

  public void setGender(String gender)
  {
    this.gender = gender;
  }

  public String getProfilePicture()
  {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture)
  {
    this.profilePicture = profilePicture;
  }
}
