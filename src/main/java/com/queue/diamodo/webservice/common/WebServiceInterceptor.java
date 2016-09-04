package com.queue.diamodo.webservice.common;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.utils.Utils;

@Service
@Aspect
public class WebServiceInterceptor {


  Logger webSocketInterceptor = Logger.getLogger(WebServiceInterceptor.class);

  @Around("execution(* com.queue.diamodo.webservice.controller.*.*(..)) ")
  public Object loggingAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    if (proceedingJoinPoint.getSignature() instanceof MethodSignature) {
      MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

      webSocketInterceptor.info(" start calling method " + signature.getMethod() + "( "
          + Utils.prepareArgsString(proceedingJoinPoint.getArgs()) + ")");
      Object value = null;
      try {
        value = proceedingJoinPoint.proceed();
      } catch (Throwable e) {

        webSocketInterceptor.error("exception happend while calling method "
            + signature.getMethod());
        if (e instanceof DiamodoCheckedException) {
          webSocketInterceptor.error(e.toString());
        } else {
          webSocketInterceptor.error("none business exception ", e);
        }
        throw e;
      }
      String result = String.format("%s", value != null ? " with result " + value : "");
      webSocketInterceptor.debug("end calling method " + signature.getMethod() + ")" + result);;
      return value;
    }
    return null;
  }


}
