package com.queue.diamodo.web.webservice.websocket;

import java.io.Serializable;
import java.util.List;

public class GetUnSeenMessagesResponse implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private List<LightMessage> messages;


  public List<LightMessage> getMessages() {
    return messages;
  }


  public void setMessages(List<LightMessage> messages) {
    this.messages = messages;
  }
  
}
