package com.queue.diamodo.webservice.common;

import java.io.Serializable;

public class BusinessError implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private int errorCode;

  private String errorMessage;



  public BusinessError() {
    super();
  }

  public BusinessError(int errorCode, String errorMessage) {
    super();
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }



}
