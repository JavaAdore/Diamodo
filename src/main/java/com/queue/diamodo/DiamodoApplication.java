package com.queue.diamodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.queue.diamodo.common.velocity.DiamodoTemplateBean;
import com.queue.diamodo.web.webservice.pushnotification.PushNotificationManager;
import com.queue.diamodo.web.webservice.pushnotification.PushNotificationMessage;
import com.queue.diamodo.web.webservice.websocket.ApplicationContextHolder;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class})
@ImportResource(locations = {"classpath:/spring.xml"})
@ComponentScan(basePackages = {"com.*"})
public class DiamodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiamodoApplication.class, args);
    PushNotificationManager pushNotificationManager =  ApplicationContextHolder.getApplicationContext().getBean(PushNotificationManager.class);
    PushNotificationMessage pushNotificationMessage = new PushNotificationMessage();
    pushNotificationMessage.setMsg("test test test");
    pushNotificationMessage.setToken("fv8DpUOPEWU:APA91bHEM2ZsLI0KZl28hta6-FlZTSvMV_jzs6TSh1Ofl6MABGaA25PPK7vs6Z8h2Iz0l0jkNHhAZK418HGw3hDjq_pckrahNsoOoU0h7gH03oxmZxkZ_qy5KM0vTYOYOA6RnDgDEGTf");
    pushNotificationMessage.setPlatform(1);
    pushNotificationManager.pushToTheClient(pushNotificationMessage);
  }


}
