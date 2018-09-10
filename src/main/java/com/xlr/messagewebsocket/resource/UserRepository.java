package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface repository for database queries for user entity
 *
 * Table name is users
 *
 * The queries are being determined via methods' names
 **/
public interface UserRepository extends JpaRepository<User, Long>
{
  /**
   * The method runs the following query
   *
   * select * from users where user_id = {userID}
   **/
  User findByUserID(String userID);

  /**
   * The method runs the following query
   *
   * select * from users where username = {username}
   **/
  User findByUsername(String username);

  /**
   * The method runs the following query
   *
   * select * from users where username = {userStatus}
   **/
  List<User> findByUserStatus(String userStatus);

  /**
   * The method runs the following query
   *
   * select * from users where username = {username} and passwrd = {password}
   **/
  User findByUsernameAndPassword(String username, String password);

  /**
   * The method runs the following query
   *
   * select * from users where user_role = {userRole}
   **/
  List<User> findByUserRole(String userRole);

  /**
   * The method runs the following query
   *
   * select * from users where user_id != {userID} and user_role = {userRole} and user_status != {userStatus}
   **/
  List<User> findByUserIDIsNotAndUserRoleAndUserStatusIsNot(String userID, String userRole, String userStatus);

  /**
   * The method runs the following query
   *
   * select * from users where user_id != {userID} and user_role = {userRole} and user_status = {userStatus}
   **/
  List<User> findByUserIDIsNotAndUserRoleAndUserStatus(String userID, String userRole, String userStatus);

  /**
   * The method runs the following query
   *
   * select * from users where user_role = {userRole} and user_status = {userStatus}
   **/
  List<User> findByUserRoleAndUserStatus(String userRole, String userStatus);
}
