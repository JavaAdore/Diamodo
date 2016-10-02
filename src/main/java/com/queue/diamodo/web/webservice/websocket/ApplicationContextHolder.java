package com.queue.diamodo.web.webservice.websocket;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.management.DiamodoManagement;

@Service
public class ApplicationContextHolder implements ApplicationContextAware {

  private static org.springframework.context.ApplicationContext APPLICATION_CONTEXT;

  @Override
  public void setApplicationContext(
      org.springframework.context.ApplicationContext applicationContext) throws BeansException {
    APPLICATION_CONTEXT = applicationContext;

  }


  public static org.springframework.context.ApplicationContext getApplicationContext() {
    return APPLICATION_CONTEXT;
  }


  public static <T> T createSpringBean(Class<T> class1,Object ... args) {
     return APPLICATION_CONTEXT.getBean(class1, args);
    
  }

  public static DiamodoManagement getDiamodoManagement() {
    return createSpringBean(DiamodoManagement.class);
  }
  
  
  public static <T> T createSpringBean(String name,Object ... args) {
    return (T) APPLICATION_CONTEXT.getBean(name, args);
   
 }

}
