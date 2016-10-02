package com.queue.diamodo.web.webservice.websocket;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.UID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.ChatMessageType;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;

@Service
public class WebSocketDTOFactory {


  @Autowired
  private DiamodoConfigurations diamodoConfiguration;

  public OutboundChatSocketMessage prepareOutboundChatSocketMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {

    switch (inboundSocketChatMessage.getChatMessageType()) {
      case ChatMessageType.MESSAEG_TYPE_TEXT_MESSAGE:

        return prepareTextMessage(inboundSocketChatMessage);

      case ChatMessageType.MESSAEG_TYPE_BUZZ_MESSAGE:
        return prepareBuzzMessage(inboundSocketChatMessage);

      case ChatMessageType.MESSAEG_TYPE_BASE64_IMAGE_MESSAGE:

        return prepareBase64ImageMessage(inboundSocketChatMessage);
    }


    return prepareServerErrorOutboundChatSocketMessage(inboundSocketChatMessage);
  }



  private OutboundChatSocketMessage prepareBase64ImageMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {
    try {
      String imageName =
          Utils.generateRandomToken() + "-" + Utils.generateRandomToken() + "-"
              + Utils.generateRandomToken();


      Utils.saveImageToServer(
          inboundSocketChatMessage.getMessageContent(),
          diamodoConfiguration.DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CHAT_MESSAGE_FILES_FOLDER_LOCATION
              + File.separator + imageName + ".png");
      OutboundChatSocketMessage outboundChatSocketMessage =
          prepareBaseOutboundChatSocketMessage(inboundSocketChatMessage);
      outboundChatSocketMessage.setMessageContent(imageName + ".png");
      return outboundChatSocketMessage;
    } catch (Exception ex) {
      ex.printStackTrace();
      return prepareBaseOutboundChatSocketMessage(inboundSocketChatMessage);
    }
  }



  private OutboundChatSocketMessage prepareServerErrorOutboundChatSocketMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {

    OutboundChatSocketMessage outboundChatSocketMessage =
        prepareBaseOutboundChatSocketMessage(inboundSocketChatMessage);
    outboundChatSocketMessage
        .setMessageContent(diamodoConfiguration.DEFAULT_SERVER_ERROR_SOCKET_MESSAGE);
    return outboundChatSocketMessage;


  }


 
 


  private OutboundChatSocketMessage prepareBuzzMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {

    OutboundChatSocketMessage outboundChatSocketMessage = prepareBaseOutboundChatSocketMessage(inboundSocketChatMessage);
    outboundChatSocketMessage.setMessageContent("BUZZ!!");
    return outboundChatSocketMessage; 
  }



  private OutboundChatSocketMessage prepareTextMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {

    OutboundChatSocketMessage outboundChatSocketMessage =
        prepareBaseOutboundChatSocketMessage(inboundSocketChatMessage);
    outboundChatSocketMessage.setMessageContent(inboundSocketChatMessage.getMessageContent());
    return outboundChatSocketMessage;
  }



  private OutboundChatSocketMessage prepareBaseOutboundChatSocketMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {

    OutboundChatSocketMessage outboundChatSocketMessage = new OutboundChatSocketMessage();
    outboundChatSocketMessage.setId(Utils.generateRandomToken());
    outboundChatSocketMessage.setDate(Utils.getTimeInUTC());
    outboundChatSocketMessage.setChatMessageType(inboundSocketChatMessage.getChatMessageType());
    outboundChatSocketMessage.setSenderId(inboundSocketChatMessage.getSenderId());
    outboundChatSocketMessage.setDestinationId(inboundSocketChatMessage.getDestinationId());
    outboundChatSocketMessage.setMessageContent("");
    return outboundChatSocketMessage;
  }



}
