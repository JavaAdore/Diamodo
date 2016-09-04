package com.queue.diamodo.webservice.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class SocketMessageDecoder implements Decoder.Text<SocketMessage> {

  private final static Logger INBOUND_SOCKET_MESSAGE_DECODER_LOGGER = Logger
      .getLogger(SocketMessageDecoder.class);

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(EndpointConfig arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public SocketMessage decode(String encodedString) throws DecodeException {

    try {
      INBOUND_SOCKET_MESSAGE_DECODER_LOGGER.debug("SocketMessage arrived " + encodedString);
      Gson gson = new Gson();
      SocketMessage SocketMessage =
          gson.fromJson(encodedString, SocketMessage.class);
      INBOUND_SOCKET_MESSAGE_DECODER_LOGGER.debug("SocketMessage decoded successfully  "
          + encodedString);

      return SocketMessage;
    } catch (Exception ex) {
      INBOUND_SOCKET_MESSAGE_DECODER_LOGGER.error("error while decoding message  " + encodedString,
          ex);

      throw new DecodeException(encodedString,
          "unable to decode SocketMessage Decoder message ", ex);
    }
  }

  @Override
  public boolean willDecode(String arg0) {

    return true;
  }


}
