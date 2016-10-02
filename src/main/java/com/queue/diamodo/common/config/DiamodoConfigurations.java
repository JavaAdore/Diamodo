package com.queue.diamodo.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.queue.diamodo.web.webservice.websocket.UserSessionsHolder;

@Configuration
@PropertySource("classpath:DiamodoConfigurations.properties")
@Scope("singleton")
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


  @Value("${DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY:5000}")
  public long DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY;


  @Value("${DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CHAT_MESSAGE_FILES_FOLDER_LOCATION}")
  public String DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CHAT_MESSAGE_FILES_FOLDER_LOCATION;

  
  @Value("${DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CONVERSATION_COVER_PHOTO_FILES_FOLDER_LOCATION}")
  public String DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CONVERSATION_COVER_PHOTO_FILES_FOLDER_LOCATION;

  

  @Value("${DEFAULT_SERVER_ERROR_SOCKET_MESSAGE}")
  public String DEFAULT_SERVER_ERROR_SOCKET_MESSAGE;


  @Value("${PUSH_NOTIFICATION_MESSAGE_TO_SPECIFIC_DEVICE_API_URL}")
  public String PUSH_NOTIFICATION_MESSAGE_TO_SPECIFIC_DEVICE_API_URL;


  @Value("${X-PUSHBOTS-APPID}")
  public String X_PUSHBOTS_APPID;

  @Value("${X-PUSHBOTS-SECRET}")
  public String X_PUSHBOTS_SECRET;
  
  
  
  @Value("${PUSH_NOTIFICATION_DEFAULT_MESSAGE_CONTENT_LENGTH}")
  public int PUSH_NOTIFICATION_DEFAULT_MESSAGE_CONTENT_LENGTH;




  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }


  @Bean
  @Scope("prototype")
  // As we want to create several beans with different args, right?
  public UserSessionsHolder userSessionsHolder(String userId, String userToken) {
    return new UserSessionsHolder(userId, userToken);
  }


  @Bean()
  public TaskExecutor sendingChatMessageThreadPool() {
    TaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    return threadPoolTaskExecutor;
  }

  
  @Bean
  public org.springframework.web.client.AsyncRestTemplate AsyncRestTemplate()
  {
    return new org.springframework.web.client.AsyncRestTemplate();
  }


}
