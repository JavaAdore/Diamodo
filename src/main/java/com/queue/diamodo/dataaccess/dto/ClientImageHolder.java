package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class ClientImageHolder implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String base64Image;

  public String getBase64Image() {
    return base64Image;
  }

  public void setBase64Image(String base64Image) {
    this.base64Image = base64Image;
  }



}
