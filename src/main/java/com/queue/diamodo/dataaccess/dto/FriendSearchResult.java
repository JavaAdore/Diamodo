package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.document.ProfileImage;


public class FriendSearchResult implements Serializable {



  /**
   * ''''''
   * 
   */
  private static final long serialVersionUID = 1L;



  public FriendSearchResult() {
    super();
  }



  private String id;

  private String firstName;

  private String lastName;

  private int friendshipStatus = Friendship.FRIEND_SHIP_STATUS_NOT_FRIEND_YET;

  private String email;

  private String userName;

  @JsonIgnore
  private ProfileImage currentProfileImage;



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


  public void setCurrentProfileImage(ProfileImage currentProfileImage) {
    this.currentProfileImage = currentProfileImage;
  }

  public ProfileImage getCurrentProfileImage() {
    return currentProfileImage;
  }

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

  public int getFriendshipStatus() {
    return friendshipStatus;
  }

  public void setFriendshipStatus(int friendshipStatus) {
    this.friendshipStatus = friendshipStatus;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }



}
