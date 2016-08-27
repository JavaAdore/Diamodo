package com.queue.diamodo.webservice.websocket.utils;

import java.text.DateFormat;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.GsonBuilder;
import com.queue.diamodo.dataaccess.dto.UserLocationDTO;

public class UserLocationDecoder implements Decoder.Text<UserLocationDTO> {

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

  @Override
  public void init(EndpointConfig arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public UserLocationDTO decode(String string) throws DecodeException {
    if (string != null) {
      try {
        return new GsonBuilder().setDateFormat(DateFormat.LONG).create()
            .fromJson(string, UserLocationDTO.class);

      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public boolean willDecode(String arg0) {
    return true;
  }

}
