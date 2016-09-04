package com.queue.diamodo.business.exception;


public class DiamodoCheckedException extends Exception {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private int errorCode;

  private String errorMessage;

  private Object object;



  public DiamodoCheckedException() {
    super();
  }

  public DiamodoCheckedException(int errorCode, String errorMessage) {
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

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  @Override
  public String toString() {
    return "DiamodoCheckedException [errorCode=" + errorCode + ", errorMessage=" + errorMessage
        + ", object=" + object + "]";
  }



}
