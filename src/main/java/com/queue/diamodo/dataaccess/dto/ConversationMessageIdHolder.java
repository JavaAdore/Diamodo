package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class ConversationMessageIdHolder implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private String conversationId;

  private boolean isGroupChat;

  private int chatMessageType;

  public ConversationMessageIdHolder() {
    super();
  }



  public ConversationMessageIdHolder(String conversationId, boolean isGroupChat,
      int chatMessageType) {
    super();
    this.conversationId = conversationId;
    this.isGroupChat = isGroupChat;
    this.chatMessageType = chatMessageType;
  }

  public boolean isGroupChat() {
    return isGroupChat;
  }

  public void setGroupChat(boolean isGroupChat) {
    this.isGroupChat = isGroupChat;
  }

  public int getChatMessageType() {
    return chatMessageType;
  }

  public void setChatMessageType(int chatMessageType) {
    this.chatMessageType = chatMessageType;
  }



  public String getConversationId() {
    return conversationId;
  }

  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }



}
