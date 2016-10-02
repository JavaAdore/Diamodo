package com.queue.diamodo.web.webservice.websocket;

import java.io.Serializable;

import com.queue.diamodo.common.document.ChatMessageType;

public class InboundSocketChatMessage implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;

  private String senderId;

  private String destinationId;

  private String senderToken;

  private String messageContent="";

  private int chatMessageType;



  public String getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }

  public String getSenderToken() {
    return senderToken;
  }

  public void setSenderToken(String senderToken) {
    this.senderToken = senderToken;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public int getChatMessageType() {
    return chatMessageType;
  }

  public void setChatMessageType(int chatMessageType) {
    this.chatMessageType = chatMessageType;
  }

  @Override
  public String toString() {
    return "InboundSocketChatMessage [senderId=" + senderId + ", destinationId=" + destinationId
        + ", senderToken=" + senderToken + ", messageContent=" + messageContent
        + ", chatMessageType=" + chatMessageType + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  
  public boolean isChatMessage() {
    return chatMessageType == ChatMessageType.MESSAEG_TYPE_TEXT_MESSAGE;
  }

  public boolean isImageMessage() {
    return chatMessageType == ChatMessageType.MESSAEG_TYPE_BASE64_IMAGE_MESSAGE;
  }

  public boolean isBuzz() {
    return chatMessageType == ChatMessageType.MESSAEG_TYPE_BUZZ_MESSAGE;
  }


}
