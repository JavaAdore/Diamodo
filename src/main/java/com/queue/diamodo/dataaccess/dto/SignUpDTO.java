package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

import com.queue.diamodo.common.utils.Utils;

public class SignUpDTO extends BasicClientInfoDTO implements Serializable {


  private static final long serialVersionUID = 1L;
  
  private String userToken;
  
  
  @Override
  public String toString() {
    return super.toString();
  }


  public String getUserToken() {
    return userToken;
  }


  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  
  public void assignRandomToken()
  {
    userToken = Utils.generateRandomToken();
  }





}
