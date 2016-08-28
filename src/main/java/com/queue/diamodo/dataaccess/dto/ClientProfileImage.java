package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class ClientProfileImage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String clientProfileImageURL;



  public ClientProfileImage() {
    super();
  }


  public ClientProfileImage(String clientProfileImageURL) {
    super();
    this.clientProfileImageURL = clientProfileImageURL;
  }


  public String getClientProfileImageURL() {
    return clientProfileImageURL;
  }


  public void setClientProfileImageURL(String clientProfileImageURL) {
    this.clientProfileImageURL = clientProfileImageURL;
  }



}
