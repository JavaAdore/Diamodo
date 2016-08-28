package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {

  /**
   * 
   */


  private static final long serialVersionUID = 1L;


  // email or userName
  private String loginAttribute;

  private String password;



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



}
