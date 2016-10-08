package com.queue.diamodo.web.webservice.websocket;

public interface SocketMessageType {

  public final static int SEND_MESSAGE_PEER_MESSAGE = 1;
 
  public final static int RECIEVE_MESSAGE = 2;
  
  public final static int GET_UNSEEN_MESSAGES = 3;

  public final static int GET_UNSEEN_MESSAGES_RESPONSE = 4;


  public final static int RECIEVE_FRIENDSHIP_DELETATION_MESSAGE = 5;


}
