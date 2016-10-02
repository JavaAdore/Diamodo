package com.queue.diamodo.web.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.web.webservice.common.DiamodoResponse;
import com.queue.diamodo.web.webservice.websocket.DiamodoEndPoint;
import com.queue.diamodo.web.webservice.websocket.UserSessionsHolder;

public class UserAuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	DiamodoManagement diamodoManagement;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String userToken = request.getHeader("userToken");
		String clientId = request.getHeader("clientId");
		Locale locale = request.getLocale();

		if (Utils.isEmpty(userToken) || Utils.isEmpty(clientId)) {
			DiamodoResponse diamodoResponse = DiamodoResponse.prepareFailureResponse(
					DiamodoResourceBundleUtils.USER_TOKEN_AND_CLIENT_ID_ARE_REQUIRED_CODE,
					DiamodoResourceBundleUtils.USER_TOKEN_AND_CLIENT_ID_ARE_REQUIRED_KEY, locale);

			String json = new Gson().toJson(diamodoResponse);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getOutputStream().print(json);
			return false;
		} else {
			UserSessionsHolder userSessionHolder = DiamodoEndPoint.getUserSessionHolder(clientId);
			boolean isMatched = false;
			if (Utils.isNotEmpty(userSessionHolder)) {
				isMatched = Utils.areMatched(userToken, userSessionHolder.getUserToken());

			} else {
				isMatched = diamodoManagement.isValidClientToken(clientId, userToken);
			}

			if (isMatched) {
				return true;
			}

			DiamodoResponse diamodoResponse = DiamodoResponse.prepareFailureResponse(
					DiamodoResourceBundleUtils.INVALID_USER_TOKEN_CODE,
					DiamodoResourceBundleUtils.INVALID_USER_TOKEN_KEY, locale);

			String json = new Gson().toJson(diamodoResponse);
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getOutputStream().print(json);

			return false;

		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
