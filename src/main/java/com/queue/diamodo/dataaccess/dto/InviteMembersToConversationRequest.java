package com.queue.diamodo.dataaccess.dto;

import java.util.HashSet;
import java.util.Set;

public class InviteMembersToConversationRequest {
  
  private String conversationId;
  
  private Set<String> memberIds= new HashSet<String>();

  public Set<String> getMemberIds() {
    return memberIds;
  }

  public void setMemberIds(Set<String> memberIds) {
    this.memberIds = memberIds;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  
  
}
