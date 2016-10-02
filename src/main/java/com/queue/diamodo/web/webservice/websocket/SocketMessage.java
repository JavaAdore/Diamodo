package com.queue.diamodo.web.webservice.websocket;

import java.io.Serializable;

public class SocketMessage implements Serializable {

  /**
   * 
   */

  private static final long serialVersionUID = 1L;

  private int socketMessageType;

  private Object data;



  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public static SocketMessage prepareRecieveSocketMessage(Object data) {
    SocketMessage outboundSocketMessage = new SocketMessage();
    outboundSocketMessage.setData(data);
    outboundSocketMessage.setSocketMessageType(SocketMessageType.RECIEVE_MESSAGE);
    return outboundSocketMessage;
  }

  public static SocketMessage prepareRecieveGetUnseenMessagesResponse(Object data) {
    SocketMessage outboundSocketMessage = new SocketMessage();
    outboundSocketMessage.setData(data);
    outboundSocketMessage.setSocketMessageType(SocketMessageType.GET_UNSEEN_MESSAGES_RESPONSE);
    return outboundSocketMessage;
  }


  public boolean isChatMessage() {
    return socketMessageType == SocketMessageType.SEND_MESSAGE_PEER_MESSAGE;
  }


  public boolean isGetUnseenMessage() {
    return socketMessageType == SocketMessageType.GET_UNSEEN_MESSAGES;
  }

  public int getSocketMessageType() {
    return socketMessageType;
  }

  public void setSocketMessageType(int socketMessageType) {
    this.socketMessageType = socketMessageType;
  }

  @Override
  public String toString() {
    return "SocketMessage [socketMessageType=" + socketMessageType + ", data=" + data + "]";
  }



}
