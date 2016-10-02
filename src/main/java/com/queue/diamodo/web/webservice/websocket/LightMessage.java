package com.queue.diamodo.web.webservice.websocket;

import java.io.Serializable;
import java.util.Date;

public class LightMessage implements Serializable{

  /**
   * 
   */
  
  private String id;
  
  private static final long serialVersionUID = 1L;
  
  private String senderId;
  
  private String messageContent="";
  
  private int chatMessageType;
  
  private Date date;

  

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public int getChatMessageType() {
    return chatMessageType;
  }

  public void setChatMessageType(int chatMessageType) {
    this.chatMessageType = chatMessageType;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "LightMessage [messageId=" + id + ", senderId=" + senderId + ", messageContent="
        + messageContent + ", chatMessageType=" + chatMessageType + ", date=" + date + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  

}
