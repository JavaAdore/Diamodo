package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

import com.queue.diamodo.common.utils.Utils;

public class LoginDTO implements Serializable {

  /**
   * 
   */


  private static final long serialVersionUID = 1L;


  // email or userName
  private String loginAttribute;

  private String password;

  private String userToken;

  public String getLoginAttribute() {
    return loginAttribute;
  }

  public void setLoginAttribute(String loginAttribute) {
    this.loginAttribute = loginAttribute;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  public void assignRandomToken()
  {
    userToken = Utils.generateRandomToken();
  }

  @Override
  public String toString() {
    return "LoginDTO [loginAttribute=" + loginAttribute + ", password=" + password + "]";
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }



}
