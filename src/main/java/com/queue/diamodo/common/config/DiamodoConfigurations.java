package com.queue.diamodo.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:DiamodoConfigurations.properties")
public class DiamodoConfigurations {



  @Value("${DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION}")
  public String DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION;

  @Value("${DEFAULT_NUMBER_OF_RESULT_NEEDED_IN_SEARCH_RESULT:10}")
  public int DEFAULT_NUMBER_OF_RESULT_NEEDED_IN_SEARCH_RESULT;

  @Value("${MAX_NUMBER_OF_RESULT_ALLOWED_PER_FRIENDS_SEARCH:50}")
  public int MAX_NUMBER_OF_RESULT_ALLOWED_PER_FRIENDS_SEARCH;


  @Value("${MAX_NUMBER_OF_TOTAL_SEARCH_RESULT_FOR_USER:10000}")
  public int MAX_NUMBER_OF_TOTAL_SEARCH_RESULT_FOR_USER = 0;

  @Value("${DEFAULT_PROFILE_IMAGE_FORMAT:jpg}")
  public String DEFAULT_PROFILE_IMAGE_FORMAT;



  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }


}
