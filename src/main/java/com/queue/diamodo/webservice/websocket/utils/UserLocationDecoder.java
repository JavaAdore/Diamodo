package com.queue.diamodo.webservice.websocket.utils;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class UserLocationDecoder implements Decoder.Text<Object> {

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(EndpointConfig arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public Object decode(String string) throws DecodeException {

    return null;
  }

  @Override
  public boolean willDecode(String arg0) {
    return true;
  }

}
