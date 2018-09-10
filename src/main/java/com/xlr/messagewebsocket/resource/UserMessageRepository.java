package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.model.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface repository for database queries for user message entity
 *
 * Table name is user_messages
 *
 * The queries are being determined via methods' names
 **/
public interface UserMessageRepository extends JpaRepository<UserMessage, Long>
{
  /**
   * The method runs the following query
   *
   * select * from users, user_messages where users.id = {toUser.id} and users.id = user_messages.to_user_id
   **/
  List<UserMessage> findByToUser(User toUser);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where users.id = {toUser.id} and users.id = user_messages.to_user_id order by send_date desc
   **/
  List<UserMessage> findByToUserOrderBySendDateDesc(User toUser);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where users.id = {toUser.id} and users.id = user_messages.to_user_id and is_read = {isRead}
   **/
  List<UserMessage> findByToUserAndIsRead(User toUser, String isRead);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where (users.id = {fromUser.id} and users.id = user_messages.from_user_id) or
   *                                          (users.id = {toUser.id} and users.id = user_messages.to_user_id)
   **/
  List<UserMessage> findByFromUserOrToUser(User fromUser, User toUser);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where users.id = {fromUser.id} and users.id = user_messages.from_user_id and
   *                                          send_date between {startDate} and {endDate}
   **/
  List<UserMessage> findBySendDateBetweenAndFromUser(LocalDateTime startDate, LocalDateTime endDate, User fromUser);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where send_date between {startDate} and {endDate}
   **/
  List<UserMessage> findBySendDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  /**
   * The method runs the following query
   *
   * select * from users, user_messages where users.id = {fromUser.id} and users.id = user_messages.from_user_id
   **/
  List<UserMessage> findByFromUser(User user);
}
