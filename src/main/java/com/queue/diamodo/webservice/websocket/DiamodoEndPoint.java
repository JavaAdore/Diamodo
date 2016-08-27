package com.queue.diamodo.webservice.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.queue.diamodo.dataaccess.dto.UserLocationDTO;
import com.queue.diamodo.webservice.websocket.utils.UserLocationDecoder;

@Component
@ServerEndpoint(value = "/userLocationSocket", decoders = {UserLocationDecoder.class})
public class DiamodoEndPoint {



  @OnMessage
  public void onMessage(UserLocationDTO userLocationDTO) {


  }

  @OnOpen
  public void myOnOpen(Session session) {
    System.out.println("WebSocket opened: " + session.getId());
  }

  @OnClose
  public void myOnClose(CloseReason reason) {
    System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
  }

}
