package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.queue.diamodo.common.document.ProfileImage;

public class ClientInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private String id;

  private String firstName;

  private String lastName;

  private String email;

  @JsonIgnore
  private ProfileImage currentProfileImage;


  @JsonProperty(value = "profileImage")
  public String profileImage() {
    return (currentProfileImage != null && currentProfileImage.getProfilePictureName() != null) ? currentProfileImage
        .getProfilePictureName() : null;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ProfileImage getCurrentProfileImage() {
    return currentProfileImage;
  }

  public void setCurrentProfileImage(ProfileImage currentProfileImage) {
    this.currentProfileImage = currentProfileImage;
  }

  @Override
  public String toString() {
    return "FriendInfo [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
        + ", email=" + email + ", currentProfileImage=" + currentProfileImage + "]";
  }



}
