package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.model.Conversation;
import com.xlr.messagewebsocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface repository for database queries for conversation entity
 *
 * Table name is conversations
 *
 * The queries are being determined via methods' names
 **/
public interface ConversationRepository extends JpaRepository<Conversation, Long>
{
  /**
   * The method runs the following query
   *
   * select * from users, conversations where users.id = {user.id} and users.id = conversations.user_id
   **/
  List<Conversation> findByUser(User user);

  /**
   * The method runs the following query
   *
   * select * from conversations where conversation_status = {conversationStatus}
   **/
  List<Conversation> findByConversationStatus(String conversationStatus);

  /**
   * The method runs the following query
   *
   * select * from conversations where customer_id = {customerID} and is_continue = {isContinue}
   **/
  Conversation findByCustomerIDAndIsContinue(String customerID, String isContinue);

  /**
   * The method runs the following query
   *
   * select * from users, conversations where channel_id = {channelID} and soruce_receiver_id = {sourceReceiverID} and
   *                                          source_type = {sourceType} and channel_user_id = {channelUserID} and
   *                                          cnvrst_id = {cnvrstID} and is_continue = {isContinue}
   **/
  Conversation findByChannelIDAndSourceReceiverIDAndSourceTypeAndChannelUserIDAndCnvrstIDAndIsContinue(
          String channelID, String sourceReceiverID, String sourceType, String channelUserID, String cnvrstID, String isContinue
  );

  /**
   * The method runs the following query
   *
   * select * from users, conversations where users.id = {user.id} and users.id = conversations.user_id and conversation_status = {conversationStatus}
   **/
  List<Conversation> findByUserAndConversationStatus(User user, String conversationStatus);

  /**
   * The method runs the following query
   *
   * select * from conversations where conversation_id = {conversationID} and is_continue = {isContinue}
   **/
  Conversation findByConversationIDAndIsContinue(String conversationID, String isContinue);

  /**
   * The method runs the following query
   *
   * select * from conversations where conversation_id = {conversationID}
   **/
  Conversation findByConversationID(String conversationID);

  /**
   * The method runs the following query
   *
   * select * from users, conversations where users.id = {user.id} and users.id = conversations.user_id and
   *                                   conversation_date between {startDate} and {endDate}
   **/
  List<Conversation> findByConversationDateBetweenAndConversationStatusAndUser(LocalDateTime startDate, LocalDateTime endDate,
                                                                               String conversationStatus, User user);

  /**
   * The method runs the following query
   *
   * select * from conversations where users.id = {user.id} and users.id = conversations.user_id and conversationStatus = {conversationStatus}
   **/
  List<Conversation> findByConversationDateBetweenAndConversationStatus(LocalDateTime startDate, LocalDateTime endDate,
                                                                        String conversationStatus);
}
