package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class CreateConversationResponse implements Serializable{

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;
  
  private String currentConversationImage;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCurrentConversationImage() {
    return currentConversationImage;
  }

  public void setCurrentConversationImage(String currentConversationImage) {
    this.currentConversationImage = currentConversationImage;
  }
  
  
}
