package com.xlr.messagewebsocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents attachments in the sent messages
 **/
public class MessageAttachment
{
  @JsonProperty("Url")
  private String url;

  @JsonProperty("ContentType")
  private String contentType;

  @JsonProperty("AttachmentName")
  private String attachmentName;

  @JsonProperty("BinaryData")
  private byte[] binaryData;

  public MessageAttachment()
  {
  }

  public MessageAttachment(String url, String contentType, String attachmentName, byte[] binaryData)
  {
    this.url = url;
    this.contentType = contentType;
    this.attachmentName = attachmentName;
    this.binaryData = binaryData;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getContentType()
  {
    return contentType;
  }

  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }

  public String getAttachmentName()
  {
    return attachmentName;
  }

  public void setAttachmentName(String attachmentName)
  {
    this.attachmentName = attachmentName;
  }

  public byte[] getBinaryData()
  {
    return binaryData;
  }

  public void setBinaryData(byte[] binaryData)
  {
    this.binaryData = binaryData;
  }
}
