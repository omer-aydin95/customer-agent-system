package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents SMC API user for authorization and authentication
 **/
public class SMCUser
{
  @JsonProperty("USR")
  private String username;

  @JsonProperty("PWD")
  private String password;

  public SMCUser()
  {
  }

  public SMCUser(String username, String password)
  {
    this.username = username;
    this.password = password;
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
}
