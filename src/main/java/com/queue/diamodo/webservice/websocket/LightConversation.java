package com.queue.diamodo.webservice.websocket;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LightConversation {



  private String conversationId;
  
  private String conversationName;
  
  private boolean isGroupChat;

  private Set<String> conversationMemebersId = new HashSet<String>();


  private Date lastActionDate = new Date();

  public Set<String> getConversationMemebersId() {
    return conversationMemebersId;
  }

  public void setConversationMemebersId(Set<String> conversationMemebersId) {
    this.conversationMemebersId = conversationMemebersId;
  }


  public void addNewMemeberToConversation(String id) {
    conversationMemebersId.add(id);
    refreshLastActionDate();
  }

  private void refreshLastActionDate() {

    lastActionDate = new Date();
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  public void removeMember(String clientId) {
   conversationMemebersId.remove(clientId);
  
    
  }

  public Date getLastActionDate() {
    return lastActionDate;
  }

  public void setLastActionDate(Date lastActionDate) {
    this.lastActionDate = lastActionDate;
  }

  public String getConversationName() {
    return conversationName;
  }

  public void setConversationName(String conversationName) {
    this.conversationName = conversationName;
  }

  public boolean isGroupChat() {
    return isGroupChat;
  }

  public void setGroupChat(boolean isGroupChat) {
    this.isGroupChat = isGroupChat;
  }

 



}
