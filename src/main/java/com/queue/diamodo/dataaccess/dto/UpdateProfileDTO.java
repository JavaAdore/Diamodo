package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class UpdateProfileDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  
  private String id;
  
  private String firstName;

  private String lastName;

  private String email;

  private String userName;

  private String password;
  
  ClientImageHolder imageHolder;


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


  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public String toString() {
    return "UpdateProfileDTO [firstName=" + firstName + ", lastName=" + lastName + ", email="
        + email + ", userName=" + userName + ", password=" + password + ", imageHolder"
        + imageHolder + "]";
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public ClientImageHolder getImageHolder() {
    return imageHolder;
  }


  public void setImageHolder(ClientImageHolder imageHolder) {
    this.imageHolder = imageHolder;
  }

}
