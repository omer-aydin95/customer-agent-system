package com.xlr.messagewebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@Deprecated
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{
  /**
   * Create stomp websocket endpoint
   *
   * Applications will open websocket over this endpoint
   *
   * For example http://192.168.2.1:8080/message-websocket
   **/
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry)
  {
    registry.addEndpoint("/message-websocket").setAllowedOrigins("*").withSockJS();
  }

  /**
   * Create the application message url that will be sent message from client
   *
   * For example the client should register to /customer/message
   * In this application there should be unique id between customer and message
   * For example /customer/{id}/message
   * The application will recognizes the client over this url
   *
   * The client should send message to /app/message
   * After that the application will determine the receiver
   * For example /agent/{id}/message
   **/
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry)
  {
    registry.enableSimpleBroker("/customer", "/agent");
    registry.setApplicationDestinationPrefixes("/app");
  }
}
