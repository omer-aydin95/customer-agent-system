package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents response of external API when request new conversation token
 **/
public class MessageToken
{
  @JsonProperty("TOKEN")
  private String token;

  @JsonProperty("STS")
  private int sts;

  @JsonProperty("EXP")
  private String exp;

  public MessageToken()
  {
  }

  public MessageToken(String token, int sts, String exp)
  {
    this.token = token;
    this.sts = sts;
    this.exp = exp;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

  public int getSts()
  {
    return sts;
  }

  public void setSts(int sts)
  {
    this.sts = sts;
  }

  public String getExp()
  {
    return exp;
  }

  public void setExp(String exp)
  {
    this.exp = exp;
  }
}
