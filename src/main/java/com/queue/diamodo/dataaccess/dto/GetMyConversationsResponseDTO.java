package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class GetMyConversationsResponseDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String id;

  private String conversationName;

  private String conversationImage;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getConversationName() {
    return conversationName;
  }

  public void setConversationName(String conversationName) {
    this.conversationName = conversationName;
  }

  public String getConversationImage() {
    return conversationImage;
  }

  public void setConversationImage(String conversationImage) {
    this.conversationImage = conversationImage;
  }
  
  
  
  



}
