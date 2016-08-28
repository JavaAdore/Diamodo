package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;
import java.util.Date;

public class UserInfoDTO extends SignUpDTO implements Serializable {


  /**
   * just extends basic informations adding id into it
   */
  private static final long serialVersionUID = 1L;

  private String id;

  private String userToken;

  private Date registrationDate;

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



}
