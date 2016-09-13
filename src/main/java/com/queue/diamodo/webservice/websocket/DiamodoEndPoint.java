package com.queue.diamodo.webservice.websocket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dto.ClientInfo;

@ServerEndpoint(value = "/userChatSocket/{clientId}/{userToken}",
    decoders = {SocketMessageDecoder.class}, encoders = {SocketMessageEncoder.class})
public class DiamodoEndPoint extends ApplicationContextHolder {

  Logger logger = LogManager.getLogger(DiamodoEndPoint.class);

  private final static Map<String, UserSessionsHolder> USERS_SESSIONS_MAP =
      new HashMap<String, UserSessionsHolder>();

  private final static Map<String, LightConversation> LIGHT_CONVERSATIONS_MAP =
      new HashMap<String, LightConversation>();

  DiamodoManagement diamodoManagement;


  TaskExecutor taskExecutor;

  // initialization of diamodoManagement
  {
    diamodoManagement = createSpringBean(DiamodoManagement.class);
    taskExecutor = createSpringBean(TaskExecutor.class);
  }



  @OnMessage
  public void onMessage(SocketMessage inboundSocketMessage) {

    try {

      if (inboundSocketMessage.isChatMessage()) {
        handleChatMessage(inboundSocketMessage);
      } else if (inboundSocketMessage.isGetUnseenMessage()) {
        handleGetUnseenMessages(inboundSocketMessage);
      }


    } catch (Exception ex) {
      ex.printStackTrace();
    }

  } 

  private void handleGetUnseenMessages(SocketMessage inboundSocketMessage) {
    GetUnSeenMessagesRequest getUnSeenMessagesRequest =
        parseSocketMessageData(inboundSocketMessage.getData(), GetUnSeenMessagesRequest.class);
    validateGetUnseenMessageRequest(getUnSeenMessagesRequest);

    List<OutboundChatSocketMessage> unseenMessages =
        diamodoManagement.getUnseenMessages(getUnSeenMessagesRequest.getClientId(),
            getUnSeenMessagesRequest.getConversationId());

    sendGetUnseenMessage(unseenMessages, getUnSeenMessagesRequest.getClientId());



  }

  private void sendGetUnseenMessage(List<OutboundChatSocketMessage> unseenMessages, String clientId) {

    SocketMessage outboundSocketMessage =
        SocketMessage.prepareRecieveGetUnseenMessagesResponse(unseenMessages);

    UserSessionsHolder userSessionsHolder = getUserSessionHolder(clientId);
    userSessionsHolder.recieveSocketMessage(outboundSocketMessage);



  }

  private void validateGetUnseenMessageRequest(GetUnSeenMessagesRequest getUnSeenMessagesRequest) {
    // TODO Auto-generated method stub

  }

  private void handleChatMessage(SocketMessage inboundSocketMessage) throws DiamodoCheckedException {

    InboundSocketChatMessage inboundSocketChatMessage =
        parseSocketMessageData(inboundSocketMessage.getData(), InboundSocketChatMessage.class);

    validateInputSocketChatMessage(inboundSocketChatMessage);


    if (!isCachedConversation(inboundSocketChatMessage.getDestinationId())) {

    Conversation conversation =   diamodoManagement.ensureConversationExistance(inboundSocketChatMessage.getSenderId(),
          inboundSocketChatMessage.getDestinationId());

      List<String> memberIds =
          diamodoManagement.getConversationMemberIds(inboundSocketChatMessage.getDestinationId());

      // ensure sender is still part of conversation

      cacheConversation(conversation, memberIds);



    }
    OutboundChatSocketMessage outboundChatSocketMessage =
        prepareOutboundSocketChatMessage(inboundSocketChatMessage);

    LightConversation conversation =
        getCachedConversation(inboundSocketChatMessage.getDestinationId());
    conversation.getConversationMemebersId().parallelStream().forEach(memberId -> {

      boolean peerHasActiveSession = isUserHasActiveSession(memberId);



      if (peerHasActiveSession) {
        // sending message to sender and reciever

        sendSocketChatMessageTo(outboundChatSocketMessage, memberId);
        diamodoManagement.markConversationAsSeen(memberId, outboundChatSocketMessage.getDestinationId());  

      } else {

        sendPushNotificationToMember(conversation , inboundSocketChatMessage, memberId);
      }


    });

    saveMessageToConversation(inboundSocketChatMessage.getDestinationId(),
        outboundChatSocketMessage);


  }

  private void saveMessageToConversation(final String conversationId,
      final OutboundChatSocketMessage outboundChatSocketMessage) {

    if (outboundChatSocketMessage != null) {
      taskExecutor.execute(new Runnable() {

        @Override
        public void run() {

          diamodoManagement.saveMessageToConversatation(conversationId, outboundChatSocketMessage);
        }

      });

    }

  }

  private void validateInputSocketChatMessage(InboundSocketChatMessage inboundSocketChatMessage) {

    if (Utils.isEmpty(inboundSocketChatMessage.getDestinationId())
        || Utils.isEmpty(inboundSocketChatMessage.getSenderId())) {
      UserSessionsHolder userSessionHolder =
          USERS_SESSIONS_MAP.get(inboundSocketChatMessage.getSenderId());
      if (Utils.isNotEmpty(userSessionHolder)) {

      }
    }
  }

  private void cacheConversation(Conversation conversation, List<String> memberIds) {

    LightConversation lightConversation = new LightConversation();
    lightConversation.setConversationMemebersId(new HashSet<String>(memberIds));
    lightConversation.setGroupChat(conversation.isGroupChat());
    lightConversation.setConversationName(conversation.getConversationName()); 
    lightConversation.setConversationId(conversation.getId());    
    LIGHT_CONVERSATIONS_MAP.put(conversation.getId(), lightConversation);



  }

  private void sendPushNotificationToMember(final LightConversation conversation, final InboundSocketChatMessage inboundSocketChatMessage,
      final String memberId) {
    // TODO Auto-generated method stub
    logger.info("sending push notification to " + memberId);
    logger.info("message is " + inboundSocketChatMessage);
    
    taskExecutor.execute(new Runnable()
    {

      @Override
      public void run() {
            diamodoManagement.sendPushNotificationForChatMessage(conversation , inboundSocketChatMessage , memberId);

      }
      
    });
    
   
  }

  private OutboundChatSocketMessage sendSocketChatMessageTo(
      OutboundChatSocketMessage outboundChatSocketMessage, String... ids) {


    SocketMessage outboundSocketMessage =
        SocketMessage.prepareRecieveSocketMessage(outboundChatSocketMessage);
    for (String clientId : ids) {
      UserSessionsHolder userSessionsHolder = getUserSessionHolder(clientId);
      userSessionsHolder.recieveSocketMessage(outboundSocketMessage);

    }
    return outboundChatSocketMessage;

  }



  private UserSessionsHolder getUserSessionHolder(String clientId) {
    return USERS_SESSIONS_MAP.get(clientId);
  }

  private <T> T parseSocketMessageData(Object data, Class<T> class1) {

    T t = Utils.JsonToObject(data, class1);
    if (t == null) {
      throw new RuntimeException("unable to parse " + data + " to class " + class1);
    }
    return t;
  }



  private boolean isUserHasActiveSession(String destinationId) {
    UserSessionsHolder userSessionsHolder = USERS_SESSIONS_MAP.get(destinationId);
    if (Utils.isNotEmpty(userSessionsHolder)) {
      boolean hasActiveSession = userSessionsHolder.hasActiveSession();

      return hasActiveSession;
    }
    return false;
  }



  private boolean isCachedConversation(String conversationId) {


    return LIGHT_CONVERSATIONS_MAP.get(conversationId) != null;
  }

  private LightConversation getCachedConversation(String conversationId) {

    return LIGHT_CONVERSATIONS_MAP.get(conversationId);
  }

  private void validateMessageSender(String senderId) throws DiamodoCheckedException {

    diamodoManagement.validateClientExistance(senderId);
  }


  @OnOpen
  public void onOpen(Session session, @PathParam("clientId") String clientId,
      @PathParam("userToken") String userToken) {
    System.out.println("connection to websocket " + clientId + "  " + userToken);
    if (validateClientAndUserToken(clientId, userToken)) {

      registerUserSession(clientId, session);

    }
  }

  public static void registerUserSession(String clientId, Session session) {

    UserSessionsHolder userSessionHolder = USERS_SESSIONS_MAP.get(clientId);
    if (userSessionHolder != null) {
      userSessionHolder.registerSession(session);
    }

  }

  private boolean validateClientAndUserToken(String clientId, String userToken) {
    UserSessionsHolder userSessionsHolder = USERS_SESSIONS_MAP.get(clientId);
    if (Utils.isNotEmpty(userSessionsHolder)) {
      String registeredUserToken = userSessionsHolder.getUserToken();
      if (Utils.isNotEmpty(registeredUserToken)) {
        boolean areMatched = Utils.areMatched(userToken, registeredUserToken);
        return areMatched;
      }
    } else {
      boolean isValid = diamodoManagement.isValidClientToken(clientId, userToken);
      if (isValid) {
        reigsterClientInWebsocketEndpoint(clientId, userToken);
      }
    }


    return true;
  }

  @OnClose
  public void myOnClose(CloseReason reason) {
    System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
  }

  public static void reigsterClientInWebsocketEndpoint(String id, String uuid) {

    UserSessionsHolder usersSessionsHolder = USERS_SESSIONS_MAP.get(id);
    if (Utils.isEmpty(usersSessionsHolder)) {
      usersSessionsHolder = createSpringBean(UserSessionsHolder.class, id, uuid);
      USERS_SESSIONS_MAP.put(id, usersSessionsHolder);
    } else {
      usersSessionsHolder.setUserToken(uuid);
    }



  }

  public static void terminateUserSession(String userId) {

    UserSessionsHolder userSessionsHolder = USERS_SESSIONS_MAP.get(userId);

    USERS_SESSIONS_MAP.remove(userId);
    if (userSessionsHolder != null) {
      notifyUserFriendsByUserSignOut(userSessionsHolder.getUserId());
    }

  }

  private static void notifyUserFriendsByUserSignOut(String userId) {
    // TODO Auto-generated method stub

  }


  public OutboundChatSocketMessage prepareOutboundSocketChatMessage(
      InboundSocketChatMessage inboundSocketChatMessage) {
    WebSocketDTOFactory webSocketDTOFactory = createSpringBean(WebSocketDTOFactory.class);

    OutboundChatSocketMessage outboundChatSocketMessage =
        webSocketDTOFactory.prepareOutboundChatSocketMessage(inboundSocketChatMessage);
    return outboundChatSocketMessage;
  }

  public static void leaveConversation(String clientId, String conversationId) {
    LightConversation lightConversation = LIGHT_CONVERSATIONS_MAP.get(conversationId);
    if (lightConversation != null) {
      lightConversation.removeMember(clientId);
    }

  }

}
