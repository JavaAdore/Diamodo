package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateConversationRequest implements Serializable{

  /**
   * 
   */
  
  
  private static final long serialVersionUID = 1L;
  
  private String conversationName;

  
  private String conversationImage;
  
  
  private boolean isPublicConversation;
  
  private List<String> memberIds = new ArrayList<String>();
  
  




 


  public List<String> getMemberIds() {
    return memberIds;
  }


  public void setMemberIds(List<String> memberIds) {
    this.memberIds = memberIds;
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


  public boolean isPublicConversation() {
    return isPublicConversation;
  }


  public void setPublicConversation(boolean isPublicConversation) {
    this.isPublicConversation = isPublicConversation;
  }

  
  
  
  
}
