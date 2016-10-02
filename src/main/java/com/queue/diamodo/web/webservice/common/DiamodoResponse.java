package com.queue.diamodo.web.webservice.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;

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

	public static DiamodoResponse prepareSuccessResponse(String message,
			Locale locale) {
		DiamodoResponse diamodoResponse = new DiamodoResponse();
		diamodoResponse.setMessage(DiamodoResourceBundleUtils.getValue(message,
				locale));
		;
		return diamodoResponse;

	}

	public static DiamodoResponse prepareFailureResponse(
			DiamodoCheckedException ex, Locale locale) {

		DiamodoResponse diamodoResponse = new DiamodoResponse();
		if (ex.getErrorCode() != 0 && ex.getErrorMessage() != null) {
			diamodoResponse.setResult(new BusinessError(ex.getErrorCode(),
					DiamodoResourceBundleUtils.getValue(ex.getErrorMessage(),
							locale)));
		}
		return diamodoResponse;
	}

	public static DiamodoResponse prepareBackendErrorResponse(Locale locale) {
		DiamodoResponse diamodoResponse = new DiamodoResponse();

		diamodoResponse
				.setResult(new BusinessError(
						DiamodoResourceBundleUtils.BACK_END_ERROR_CODE,
						DiamodoResourceBundleUtils.getValue(
								DiamodoResourceBundleUtils.BACK_END_ERROR_KEY,
								locale)));
		return diamodoResponse;
	}

	public static DiamodoResponse prepareSuccessResponse(String message,
			Object result, Locale locale) {
		DiamodoResponse diamodoResponse = new DiamodoResponse();
		diamodoResponse.setMessage(DiamodoResourceBundleUtils.getValue(message,
				locale));
		diamodoResponse.setResult(result);
		return diamodoResponse;
	}

	public static DiamodoResponse prepareDefaultSuccessResponse(Locale locale) {
		DiamodoResponse diamodoResponse = new DiamodoResponse();
		diamodoResponse.setMessage(DiamodoResourceBundleUtils.getValue(
				DiamodoResourceBundleUtils.DEFAULT_SUCCESS_MESSAGE, locale));
		return diamodoResponse;

	}

	public static DiamodoResponse prepareFailureResponse(int code, String key,
			Locale locale) {
		DiamodoResponse diamodoResponse = new DiamodoResponse();
		diamodoResponse.setResult(new BusinessError(code,
				DiamodoResourceBundleUtils.getValue(key, locale)));
		return diamodoResponse;
	}
}
