package com.queue.diamodo.business.exception;

public class DiamodoCheckedException extends Exception {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private String errorCode;

  private String errorMessage;

  private Object object;



  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }
}
