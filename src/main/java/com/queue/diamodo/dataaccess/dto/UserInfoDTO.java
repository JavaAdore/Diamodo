package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.queue.diamodo.common.document.ProfileImage;

public class UserInfoDTO extends BasicClientInfoDTO implements Serializable {


  /**
   * just extends basic informations adding id into it
   */
  private static final long serialVersionUID = 1L;

  private String id;

  private String userToken;

  private Date registrationDate;
  
  
  @JsonIgnore
  private ProfileImage currentProfileImage;


  @JsonProperty(value = "profileImage")
  public String profileImage() {
    return (currentProfileImage != null && currentProfileImage.getProfilePictureName() != null) ? currentProfileImage
        .getProfilePictureName() : null;
  }


  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String toString() {
    return "UserInfoDTO [id=" + id + ", userToken=" + userToken + ", registrationDate="
        + registrationDate + ", toString()=" + super.toString() + "]";
  }


  public ProfileImage getCurrentProfileImage() {
    return currentProfileImage;
  }


  public void setCurrentProfileImage(ProfileImage currentProfileImage) {
    this.currentProfileImage = currentProfileImage;
  }



}
