package com.queue.diamodo;

import org.springframework.boot.builder.SpringApplicationBuilder;

@SuppressWarnings("deprecation")
public class ServletInitializer extends
    org.springframework.boot.context.web.SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(DiamodoApplication.class);
  }

}
