package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CreateConversationRequest implements Serializable{

  /**
   * 
   */
  
  
  private static final long serialVersionUID = 1L;
  
  private String conversationName;

  
  private String conversationImage;
  
  
  private boolean isPublicConversation;
  
  private Set<String> memberIds = new HashSet<String>();
  
  




 


  public Set<String> getMemberIds() {
    return memberIds;
  }


  public void setMemberIds(Set<String> memberIds) {
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
