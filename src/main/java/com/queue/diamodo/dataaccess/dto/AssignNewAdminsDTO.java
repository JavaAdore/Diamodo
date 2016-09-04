package com.queue.diamodo.dataaccess.dto;

import java.util.HashSet;
import java.util.Set;

public class AssignNewAdminsDTO {
  
  private String conversationId;
  
  private Set<String> adminsIds= new HashSet<String>();

  public Set<String> getAdminsIds() {
    return adminsIds;
  }

  public void setAdminsIds(Set<String> adminsIds) {
    this.adminsIds = adminsIds;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }
  
  
}
