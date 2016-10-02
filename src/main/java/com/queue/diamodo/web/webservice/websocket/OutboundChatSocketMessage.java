package com.queue.diamodo.web.webservice.websocket;

import java.io.Serializable;

public class OutboundChatSocketMessage extends LightMessage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String destinationId;
  

  public String getDestinationId() {
    return destinationId;
  }

  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }

  @Override
  public String toString() {
    return "OutboundChatSocketMessage [destinationId=" + destinationId + ", toString()="
        + super.toString() + "]";
  }

 
    

}
