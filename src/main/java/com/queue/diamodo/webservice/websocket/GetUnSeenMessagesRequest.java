package com.queue.diamodo.webservice.websocket;

import java.io.Serializable;

public class GetUnSeenMessagesRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String conversationId;
  
  private String userToken;
  
  private String clientId;
  
  
  


  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }
  
  
  
  
  

}
