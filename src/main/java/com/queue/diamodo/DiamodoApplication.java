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


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class})
@ImportResource(locations = {"classpath:/spring.xml"})
@ComponentScan(basePackages = {"com.*"})
public class DiamodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiamodoApplication.class, args);


  }


}
