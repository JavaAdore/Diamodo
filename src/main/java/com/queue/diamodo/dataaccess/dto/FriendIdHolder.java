package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class FriendIdHolder implements Serializable{
  
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public FriendIdHolder() {
    super();
  }

  public FriendIdHolder(String friendId) {
    super();
    this.friendId = friendId;
  }

  private String friendId;
  
  

  public String getFriendId() {
    return friendId;
  }

  public void setFriendId(String friendId) {
    this.friendId = friendId;
  }

  @Override
  public String toString() {
    return "FriendIdHolder [friendId=" + friendId + "]";
  }
  

}
