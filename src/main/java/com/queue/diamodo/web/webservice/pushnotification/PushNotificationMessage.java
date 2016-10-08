package com.queue.diamodo.web.webservice.pushnotification;

import java.io.Serializable;

public class PushNotificationMessage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final static int IOS_PUSH_NOTIFICATION = 0;
  private final static int ANDROID_PUSH_NOTIFICATION = 1;

  private int platform;


  private String deviceToken;

  private Integer setbadgecount =1 ;



public Integer getSetbadgecount() {
	return setbadgecount;
}

public void setSetbadgecount(Integer setbadgecount) {
	this.setbadgecount = setbadgecount;
}

private String msg;


  private String sound = "default";

  private Object payload;

  public int getPlatform() {
    return platform;
  }

  public void setPlatform(int platform) {
    this.platform = platform;
  }

  public String getToken() {
    return deviceToken;
  }

  public void setToken(String token) {
    this.deviceToken = token;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getSound() {
    return sound;
  }

  public void setSound(String sound) {
    this.sound = sound;
  }

  public Object getPayload() {
    return payload;
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }



  public static PushNotificationMessage prepareAndroidPushNotification(String deviceToken,
      String msg) {

    return prepareAndroidPushNotification(deviceToken, msg, null);

  }



  public static PushNotificationMessage prepareAndroidPushNotification(String deviceToken,
      String msg, Object payload) {

    PushNotificationMessage pushNotificationMessage =
        prepareBasePushNotification(deviceToken, msg, payload);
    pushNotificationMessage.platform = ANDROID_PUSH_NOTIFICATION;
    return pushNotificationMessage;

  }


  public static PushNotificationMessage prepareIOSPushNotification(String deviceToken, String msg) {

    return prepareIOSPushNotification(deviceToken, msg, null);

  }


  public static PushNotificationMessage prepareIOSPushNotification(String deviceToken, String msg,
      Object payload) {

    PushNotificationMessage pushNotificationMessage =
        prepareBasePushNotification(deviceToken, msg, payload);
    pushNotificationMessage.platform = IOS_PUSH_NOTIFICATION;
    return pushNotificationMessage;

  }

  private static PushNotificationMessage prepareBasePushNotification(String deviceToken, String msg) {

    return prepareBasePushNotification(deviceToken, msg, null);

  }

  private static PushNotificationMessage prepareBasePushNotification(String deviceToken,
      String msg, Object payload) {

    PushNotificationMessage pushNotificationMessage = new PushNotificationMessage();
    pushNotificationMessage.deviceToken = deviceToken;
    pushNotificationMessage.msg = msg;
    pushNotificationMessage.payload = payload;
    return pushNotificationMessage;
  }

  @Override
  public String toString() {
    return "PushNotificationMessage [platform=" + platform + ", deviceToken=" + deviceToken
        + ", msg=" + msg + ", sound=" + sound + ", payload=" + payload + "]";
  }

  public boolean isAndroid()
  {
	  return platform == ANDROID_PUSH_NOTIFICATION;
  }
  
}
