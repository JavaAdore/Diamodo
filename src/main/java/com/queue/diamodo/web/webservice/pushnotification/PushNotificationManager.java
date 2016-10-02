package com.queue.diamodo.web.webservice.pushnotification;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.velocity.DiamodoTemplateBean;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.UserWithDeviceInfo;
import com.queue.diamodo.web.webservice.websocket.InboundSocketChatMessage;
import com.queue.diamodo.web.webservice.websocket.LightConversation;

@Service
public class PushNotificationManager {

  Logger logger = Logger.getLogger(PushNotificationManager.class);
  @Autowired
  private DiamodoTemplateBean diamodoTemplateBean;


  @Autowired
  AsyncRestTemplate asyncRestTemplate;

  @Autowired
  DiamodoConfigurations diamodoConfigurations;

  public void pushFriendshipRecievedNotification(ClientInfo friendshipRequestSender,
      UserWithDeviceInfo userWithDeviceInfo) {

    String message =
        diamodoTemplateBean.prepareFriendRequestRecievedMessage(friendshipRequestSender);
  

    Payload payload =
        Payload.prepareFriendshipRequestRecievedPayload(friendshipRequestSender.getId());

    PushNotificationMessage pushNotificationMessage =
        preparePushNotificationMessage(userWithDeviceInfo, message, payload);
    pushToTheClient(pushNotificationMessage);
  }



  public PushNotificationMessage preparePushNotificationMessage(
      UserWithDeviceInfo userWithDeviceInfo, String message, Payload payload) {

    PushNotificationMessage pushNotificationMessage = null;
    if (userWithDeviceInfo.getClientDevice().isAndroidDevice()) {

      logger.info("reciever has android device");
      pushNotificationMessage =
          PushNotificationMessage.prepareAndroidPushNotification(userWithDeviceInfo
              .getClientDevice().getDeviceToken(), message);

    } else if (userWithDeviceInfo.getClientDevice().isIOSDevice()) {
      logger.info("reciever has ios device");
      pushNotificationMessage =
          PushNotificationMessage.prepareIOSPushNotification(userWithDeviceInfo.getClientDevice()
              .getDeviceToken(), message);
    }
    pushNotificationMessage.setPayload(payload);
    return pushNotificationMessage;

  }
   

  private void pushToTheClient(PushNotificationMessage pushNotificationMessage) {
    logger.info("notification will be sent is " + pushNotificationMessage);

    if (pushNotificationMessage != null) {

      System.out.println("well message will be sent is " + pushNotificationMessage);

      HttpEntity<PushNotificationMessage> request =
          new HttpEntity<PushNotificationMessage>(pushNotificationMessage,
              getDefaultPushNotificationHeaders());

      ListenableFuture<ResponseEntity<Object>> returned =
          asyncRestTemplate.postForEntity(
              diamodoConfigurations.PUSH_NOTIFICATION_MESSAGE_TO_SPECIFIC_DEVICE_API_URL, request,
              null);


      returned.addCallback(new ListenableFutureCallback() {

        @Override
        public void onSuccess(Object result) {
          logger.info("push notification has been sent successfully");

        }

        @Override
        public void onFailure(Throwable ex) {
          ex.printStackTrace();
          logger.info("push notification has been not sent successfully");

        }

      });

    }

  }

  private HttpHeaders getDefaultPushNotificationHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    headers.add("X-PUSHBOTS-APPID", diamodoConfigurations.X_PUSHBOTS_APPID);
    headers.add("X-PUSHBOTS-SECRET", diamodoConfigurations.X_PUSHBOTS_SECRET);
    return headers;
  }

  public void pushOfflineMessageNotification(UserWithDeviceInfo userWithDeviceInfo,
      ClientInfo senderDTO, LightConversation conversation,
      InboundSocketChatMessage inboundSocketChatMessage) {
    String message =
        getApproperateConversationMessage(userWithDeviceInfo, senderDTO, conversation,
            inboundSocketChatMessage);

    Payload payload = Payload.prepareConversationMessagePayload(conversation.getConversationId());

    PushNotificationMessage pushNotification =
        preparePushNotificationMessage(userWithDeviceInfo, message, payload);

    pushToTheClient(pushNotification);



  }

  private String getApproperateConversationMessage(UserWithDeviceInfo userWithDeviceInfo,
      ClientInfo senderDTO, LightConversation conversation,
      InboundSocketChatMessage inboundSocketChatMessage) {
    String message = "";
    if (conversation.isGroupChat()) {

      if (inboundSocketChatMessage.isChatMessage()) {
        message =
            diamodoTemplateBean.prepareGroupChatMessage(senderDTO,
                conversation.getConversationName(), inboundSocketChatMessage.getMessageContent());
      } else if (inboundSocketChatMessage.isBuzz()) {
        message =
            diamodoTemplateBean.prepareGroupBuzzMessage(senderDTO,
                conversation.getConversationName());
      } else if (inboundSocketChatMessage.isImageMessage()) {
        message =
            diamodoTemplateBean.prepareGroupChatImageMessage(senderDTO,
                conversation.getConversationName());
      }



    } else {


      if (inboundSocketChatMessage.isChatMessage()) {
        message =
            diamodoTemplateBean.preparePeerToPeerChatMessage(senderDTO,
                inboundSocketChatMessage.getMessageContent());
      } else if (inboundSocketChatMessage.isBuzz()) {
        message = diamodoTemplateBean.preparePeerToPeerBuzzMessage(senderDTO);
      } else if (inboundSocketChatMessage.isImageMessage()) {
        message = diamodoTemplateBean.preparePeerToPeerImageMessage(senderDTO);
      }

    }
    return message;
  }
}
