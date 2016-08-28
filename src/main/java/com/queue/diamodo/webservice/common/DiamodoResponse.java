package com.queue.diamodo.webservice.common;

import java.io.Serializable;
import java.util.Locale;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;

public class DiamodoResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String message;

  private Object result;



  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }



  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }



  public static DiamodoResponse prepareSuccessResponse(Object obj) {
    DiamodoResponse diamodoResponse = new DiamodoResponse();
    diamodoResponse.setResult(obj);
    return diamodoResponse;

  }


  public static DiamodoResponse prepareSuccessResponse(String message) {
    DiamodoResponse diamodoResponse = new DiamodoResponse();
    diamodoResponse.setMessage(message);;
    return diamodoResponse;

  }


  public static DiamodoResponse prepareFailureResponse(DiamodoCheckedException ex, Locale locale) {


    DiamodoResponse diamodoResponse = new DiamodoResponse();
    if (ex.getErrorCode() != 0 && ex.getErrorMessage() != null) {
      diamodoResponse.setResult(new BusinessError(ex.getErrorCode(), ex.getErrorMessage()));
    }
    return diamodoResponse;
  }

  public static DiamodoResponse prepareBackendErrorResponse(Locale locale) {
    DiamodoResponse diamodoResponse = new DiamodoResponse();

    diamodoResponse.setResult(new BusinessError(DiamodoResourceBundleUtils.BACK_END_ERROR_CODE,
        DiamodoResourceBundleUtils.BACK_END_ERROR_KEY));
    return diamodoResponse;
  }

  public static DiamodoResponse prepareFailureResponse(String errorCode, String errorKey,
      Locale locale) {
    DiamodoResponse diamodoResponse = new DiamodoResponse();
    diamodoResponse.setResult(new BusinessError(DiamodoResourceBundleUtils.BACK_END_ERROR_CODE,
        DiamodoResourceBundleUtils.BACK_END_ERROR_KEY));
    return diamodoResponse;
  }

  public static DiamodoResponse prepareSuccessResponse(String message, Object result) {
    DiamodoResponse diamodoResponse = new DiamodoResponse();
    diamodoResponse.setMessage(message);
    diamodoResponse.setResult(result);
    return diamodoResponse;
  }


}
