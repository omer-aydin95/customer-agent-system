package com.xlr.messagewebsocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlr.messagewebsocket.model.MessageList;
import com.xlr.messagewebsocket.model.MessageToken;
import com.xlr.messagewebsocket.model.SMCUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Sends HTTP POST requests to external API
 *
 * API information are being read from properties file
 **/
@RestController
public class OutRestController
{
  @Value("${smc.api.get-token}")
  private String getTokenURL;

  @Value("${smc.api.send-messages}")
  private String sendMessagesURL;

  @Value("${smc.api.get-messages}")
  private String getMessagesURL;

  @Value("${smc.username}")
  private String username;

  @Value("${smc.password}")
  private String password;

  /**
   * Get new conversation token from external API
   **/
  public MessageToken getTokenFromAPI() throws IOException
  {
    SMCUser smcUser = new SMCUser(username, password);

    ObjectMapper objectToJson = new ObjectMapper();

    String json = objectToJson.writeValueAsString(smcUser);

    URL getURL = new URL(getTokenURL);
    HttpURLConnection connection = (HttpURLConnection)getURL.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
    out.writeBytes(json);
    out.flush();
    out.close();

    InputStream in = connection.getInputStream();
    BufferedReader read = new BufferedReader(new InputStreamReader(in));
    String temp = "";
    StringBuilder responseJson = new StringBuilder();
    while((temp = read.readLine()) != null)
    {
      responseJson.append(temp);
    }

    return objectToJson.readValue(responseJson.toString(), MessageToken.class);
  }

  /**
   * Post message to external API
   **/
  public String sendMessageToAPI(MessageList messageList) throws IOException
  {
    ObjectMapper objectToJson = new ObjectMapper();

    String json = objectToJson.writeValueAsString(messageList);

    URL url = new URL(sendMessagesURL);
    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
    out.writeBytes(json);
    out.flush();
    out.close();

    InputStream in = connection.getInputStream();
    BufferedReader read = new BufferedReader(new InputStreamReader(in));
    String temp = "";
    StringBuilder responseJson = new StringBuilder();
    while((temp = read.readLine()) != null)
    {
      responseJson.append(temp);
    }

    return responseJson.toString();
  }
}
