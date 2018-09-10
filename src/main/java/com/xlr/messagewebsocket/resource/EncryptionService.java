package com.xlr.messagewebsocket.resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class EncryptionService
{
  /**
   * User password encryption
   *
   * The function uses MD5
   **/
  public static String getEncryptedPassword(String originalPassword)
  {
    String generatedPassword = null;
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(originalPassword.getBytes());

      byte[] bytes = md.digest();

      StringBuilder sb = new StringBuilder();

      for(int i=0; i< bytes.length ;i++)
      {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }

      generatedPassword = sb.toString();
    }
    // For getInstance()
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }

    return generatedPassword;
  }

  /**
   * Create unique UUID for something if needed in somewhere
   **/
  public static String getUniqueID()
  {
    UUID uuid = UUID.randomUUID();

    return uuid.toString();
  }
}
