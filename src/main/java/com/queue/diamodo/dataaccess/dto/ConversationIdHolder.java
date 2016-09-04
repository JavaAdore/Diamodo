package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class ConversationIdHolder implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private String conversationId;



  public ConversationIdHolder() {
    super();
  }

  public ConversationIdHolder(String conversationId) {
    super();
    this.conversationId = conversationId;
  }

  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }



}
