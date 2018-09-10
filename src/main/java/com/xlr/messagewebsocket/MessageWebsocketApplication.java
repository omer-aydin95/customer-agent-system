package com.xlr.messagewebsocket;

import com.xlr.messagewebsocket.constant.FilePath;
import com.xlr.messagewebsocket.constant.Flag;
import com.xlr.messagewebsocket.constant.UserRole;
import com.xlr.messagewebsocket.constant.UserStatus;
import com.xlr.messagewebsocket.model.User;
import com.xlr.messagewebsocket.resource.EncryptionService;
import com.xlr.messagewebsocket.resource.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Application entry
 *
 * The general project's information:
 *
 * @author 19XLR95 (Ömer Aydın)
 * @author https://github.com/19XLR95
 * @author omer.aydin.business@gmail.com
 * @since 02.07.2018 10.00 AM
 * @version 4.0
 */
@SpringBootApplication
public class MessageWebsocketApplication
{
  public static void main(String[] args)
  {
    try
    {
      // Create SpringBoot application
      SpringApplication app = new SpringApplication(MessageWebsocketApplication.class);

      // Get machine IP address and overwrite to the application.properties file
      Properties prop = new Properties();
      prop.put("server.address", InetAddress.getLocalHost().getHostAddress());

      // Set the application properties file and run the application
      app.setDefaultProperties(prop);
      app.run(args);
    }
    // For InetAddress
    catch(UnknownHostException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Do something immediately after the application starts
   *
   * For example insert entity to database
   **/
  @Bean
  CommandLineRunner runner(Environment environment, UserRepository userRepository)
  {
    return args -> {
      // Print out the server's url and port
      // IP and port are being taken from application.properties
      System.out.println();
      System.out.println("System access url from local network:");
      System.out.println("http://" + environment.getProperty("server.address") + ":" + environment.getProperty("server.port"));
      System.out.println();

      User user = userRepository.findByUsername("system");

      String defaultUserPassword = "system";

      // If default user is not exist create one with administrative privilege
      if(user == null)
      {
        user = new User(EncryptionService.getUniqueID(), "SYSTEM", "SYSTEM",
                UserStatus.LOGOUT, "system", EncryptionService.getEncryptedPassword(defaultUserPassword),
                UserRole.ADMIN, "system@system.com", LocalDateTime.now(), Flag.GENDER_NULL, FilePath.SYSTEM_DEFAULT_AVATAR);

        userRepository.save(user);
      }

      System.out.println();
      System.out.println("System default user with administrative privilege:");
      System.out.println("  - Username: " + user.getUsername());
      System.out.println("  - Password: " + defaultUserPassword);
      System.out.println();
    };
  }
}
