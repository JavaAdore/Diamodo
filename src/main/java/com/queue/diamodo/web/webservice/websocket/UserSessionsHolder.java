package com.queue.diamodo.web.webservice.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.utils.Utils;


public class UserSessionsHolder extends Thread {

  Logger logger = Logger.getLogger(UserSessionsHolder.class);

  private static long DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY;

  static {

    DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY =
        ApplicationContextHolder.createSpringBean(DiamodoConfigurations.class).DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY;
  }
  // initialization block to start sessions garbage collections
  {
    start();
  }


  private String userId;

  private String userToken;

  private Set<Session> sessions = new HashSet<Session>();

  private Map<String, LightConversation> conversationsMap = new HashMap();


  public UserSessionsHolder(String userId, String userToken) {
    super();
    this.userId = userId;
    this.userToken = userToken;
  }


  public void run() {
    for (;;) {
      try {
        sleep(DEFAULT_SLEEP_PERIOD_FOR_CHECKING_SESSION_ACTIVITY);

        Iterator<Session> sessionsIterator = sessions.iterator();

        while (sessionsIterator.hasNext()) {
          Session session = sessionsIterator.next();
          if (!session.isOpen()) {
            sessionsIterator.remove();
          }
        }



      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }

  }



  public String getUserToken() {
    return userToken;
  }



  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }


  public String getUserId() {
    return userId;
  }


  public void setUserId(String userId) {
    this.userId = userId;
  }


  public void registerSession(Session session) {

    sessions.add(session);
  }


  public Map<String, LightConversation> getConversationsMap() {
    return conversationsMap;
  }


  public void setConversationsMap(Map<String, LightConversation> conversationsMap) {
    this.conversationsMap = conversationsMap;
  }


  public LightConversation getOrCreatelightConversation(String conversationId, List<String> memberIds) {
    LightConversation lightConversation = conversationsMap.get(conversationId);
    if (Utils.isEmpty(lightConversation)) {
      lightConversation = new LightConversation();
      lightConversation.setConversationId(conversationId);
      lightConversation.setConversationMemebersId(new HashSet<String>(memberIds));
      registerConversationInConversationsMap(conversationId, lightConversation);
    }
    return lightConversation;
  }

  private void registerConversationInConversationsMap(String conversationId,
      LightConversation lightConversation) {
    conversationsMap.put(conversationId, lightConversation);
  }






  public boolean isCachedConversation(String conversationId) {
    boolean isCachedConversation = conversationsMap.containsKey(conversationId);

    logger.debug("conversation : " + conversationId + " is "
        + (isCachedConversation == true ? "" : "not" + "cached for user " + userId));

    return isCachedConversation;
  }


  public boolean hasActiveSession() {
    for (Session session : sessions) {
      if (session.isOpen()) {
        return true;
      }
    }
    return false;
  }


  public void recieveSocketMessage(final SocketMessage outboundSocketMessage) {
    TaskExecutor taskExecutor = ApplicationContextHolder.createSpringBean(TaskExecutor.class);
    for (Session session : sessions) {
      taskExecutor.execute(new Runnable() {

        @Override
        public void run() {
          try {
            session.getBasicRemote().sendObject(outboundSocketMessage);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (EncodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      });
    }

  }



}
