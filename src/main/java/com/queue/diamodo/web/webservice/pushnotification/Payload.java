package com.queue.diamodo.web.webservice.pushnotification;

import java.io.Serializable;

import com.queue.diamodo.dataaccess.dto.ConversationIdHolder;
import com.queue.diamodo.dataaccess.dto.ConversationMessageIdHolder;
import com.queue.diamodo.dataaccess.dto.FriendshipIdHolder;
import com.queue.diamodo.web.webservice.websocket.InboundSocketChatMessage;
import com.queue.diamodo.web.webservice.websocket.LightConversation;

public class Payload implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final int FRIENDSHIP_REQUEST_RECIEVED = 1;

  public static final int CONVERSATION_MESSAGE = 2;


  private int code;

  private Object data;


  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }


  public static Payload prepareFriendshipRequestRecievedPayload(String friendId) {

    Payload payload = new Payload();
    payload.code = FRIENDSHIP_REQUEST_RECIEVED;
    payload.data = new FriendshipIdHolder(friendId);
    return payload;


  }   



  public static Payload prepareConversationMessagePayload(String conversationId) {

    Payload payload = new Payload();
    payload.code = CONVERSATION_MESSAGE;
    payload.data = new ConversationIdHolder(conversationId);
    return payload;


  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Payload [code=" + code + ", data=" + data + "]";
  }

  public static Payload prepareConversationMessagePayload(LightConversation conversation,
      InboundSocketChatMessage inboundSocketChatMessage) {
    Payload payload = new Payload();
    payload.code = CONVERSATION_MESSAGE;
    payload.data = new ConversationMessageIdHolder(conversation.getConversationId(), conversation.isGroupChat(), inboundSocketChatMessage.getChatMessageType());
    return payload;
  }



}
