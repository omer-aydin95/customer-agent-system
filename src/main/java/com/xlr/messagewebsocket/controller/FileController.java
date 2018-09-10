package com.xlr.messagewebsocket.controller;

import com.xlr.messagewebsocket.constant.FilePath;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController
{
  /**
   * Save profile picture
   * And return the path for database and html
   * For example /images/profile-img/{userID}.jpg
   **/
  public static String saveProfilePicture(MultipartFile profilePicture, String userID)
      throws IOException
  {
    byte[] pictureBytes = profilePicture.getBytes();

    String fileExtension = profilePicture.getOriginalFilename().substring(
        profilePicture.getOriginalFilename().lastIndexOf(".")
    );

    Path picturePath = Paths.get(FilePath.PICTURE_PATH + userID + fileExtension);

    Files.write(picturePath, pictureBytes);

    String pathForHtmlAndDB = FilePath.PICTURE_PATH_HTML + userID + fileExtension;

    return pathForHtmlAndDB;
  }

  /**
   * Delete existing user profile picture from storage
   **/
  public static boolean deleteFile(String picturePathForDB)
  {
    String pictureFile = picturePathForDB.substring(
        picturePathForDB.lastIndexOf("/") + 1
    );

    File deleteFile = new File(FilePath.PICTURE_PATH + pictureFile);

    return deleteFile.delete();
  }
}
