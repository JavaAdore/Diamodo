package com.queue.diamodo.dataaccess.dto;

import com.queue.diamodo.common.document.ClientDevice;

public class UserWithDeviceInfo extends UserInfoDTO{

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private ClientDevice clientDevice;


  public ClientDevice getClientDevice() {
    return clientDevice;
  }


  public void setClientDevice(ClientDevice clientDevice) {
    this.clientDevice = clientDevice;
  }
  
  
  

}
