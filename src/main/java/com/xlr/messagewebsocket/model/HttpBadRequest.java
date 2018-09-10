package com.xlr.messagewebsocket.model;

/**
 * Represents bad request of available agent request
 **/
public class HttpBadRequest
{
  private final String status = "400 - Bad Request";
  private String description;

  public HttpBadRequest()
  {
  }

  public HttpBadRequest(String description)
  {
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getStatus()
  {
    return status;
  }
}
