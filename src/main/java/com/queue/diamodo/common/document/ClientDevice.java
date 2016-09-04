package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientDevice implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private final static String IOS_DEVICE = "0";
  private final static String ANDROID_DEVICE = "1";
  private final static String WEB_BROWSER = "2";



  private final static List<String> ACCEPTED_DEVICES = new ArrayList<String>();
  static {
    ACCEPTED_DEVICES.add(ANDROID_DEVICE);
    ACCEPTED_DEVICES.add(IOS_DEVICE);
    ACCEPTED_DEVICES.add(WEB_BROWSER);

  }



  private String deviceType;


  private String deviceToken;



  public static boolean isAcceptedDevice(String device) {
    return ACCEPTED_DEVICES.contains(device);
  }



  public String getDeviceType() {
    return deviceType;
  }



  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }



  public String getDeviceToken() {
    return deviceToken;
  }



  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }



  public boolean isIOSDevice() {
    return deviceType != null && deviceType.equals(IOS_DEVICE);
  }

  public boolean isAndroidDevice() {
    return deviceType != null && deviceType.equals(ANDROID_DEVICE);
  }



  public boolean isAcceptedDevice() {
    return deviceType != null && isAcceptedDevice(deviceType);
  }


}
