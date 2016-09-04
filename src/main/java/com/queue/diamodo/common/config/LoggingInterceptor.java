package com.queue.diamodo.common.config;


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
public class LoggingInterceptor {

  static Logger loggingInterceptorLogger = Logger.getLogger(LoggingInterceptor.class.getName());

  @Around(" execution(* com.queue.diamodo.business.serviceimpl.*.*(..)) || execution(* com.queue.diamodo.business.daoimpl.*.*(..)) ||execution(* com.queue.diamodo.management.managementimpl.*.*(..))")
  public Object loggingAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    if (proceedingJoinPoint.getSignature() instanceof MethodSignature) {
      MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
     
      loggingInterceptorLogger.info(" start calling method " + signature.getMethod() + "( "
          + Utils.prepareArgsString(proceedingJoinPoint.getArgs()) + ")");
      Object value = null;
      try {
        value = proceedingJoinPoint.proceed();
      } catch (Throwable e) {

        loggingInterceptorLogger.error("exception happend while calling method "
            + signature.getMethod());
        if (e instanceof DiamodoCheckedException) {
          loggingInterceptorLogger.error(e.toString());
        }
        throw e;
      }
      String result = String.format("%s", value != null ? " with result " + value : "");
      loggingInterceptorLogger.debug("end calling method " + signature.getMethod() + ")" + result);;
      return value;
    }
    return null;
  }

  


}
