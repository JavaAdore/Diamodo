package com.queue.diamodo.web.webservice.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class SocketMessageEncoder implements Encoder.Text<SocketMessage> {

  private final static Logger OUTBOUND_SOCKET_MESSAGE_DECODER_LOGGER = Logger
      .getLogger(SocketMessageEncoder.class);

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(EndpointConfig arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public String encode(SocketMessage OutboundSocketMessage) throws EncodeException {
    try {
      return new Gson().toJson(OutboundSocketMessage);
    } catch (Exception ex) {
      OUTBOUND_SOCKET_MESSAGE_DECODER_LOGGER.error("error while encode " + OutboundSocketMessage , ex);
      throw new EncodeException(OutboundSocketMessage, "unable to encode");
    }   
  }



}
