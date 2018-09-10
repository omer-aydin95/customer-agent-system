package com.xlr.messagewebsocket.resource;

import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.constant.UserStatus;

public class AuthService
{
  private UserRepository userRepository;

  public AuthService(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  /**
   * User authorization and authentication
   **/
  public User authentication(String username, String password)
  {
    User user = null;
    user = userRepository.findByUsernameAndPassword(username,
        EncryptionService.getEncryptedPassword(password));

    if(user != null)
    {
      user.setUserStatus(UserStatus.AVAILABLE);
      userRepository.save(user);

      return user;
    }
    else
    {
      return null;
    }
  }

  /**
   * User logout operations
   **/
  public void logout(User user)
  {
    user.setUserStatus(UserStatus.LOGOUT);

    userRepository.save(user);
  }
}
