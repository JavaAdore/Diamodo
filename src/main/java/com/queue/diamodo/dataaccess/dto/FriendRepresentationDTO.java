package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class FriendRepresentationDTO implements Serializable {

  
  
  public FriendRepresentationDTO()
  {
    
  }
  
  
  public FriendRepresentationDTO(String id, FriendInfo friend) {
    super();
    this.id = id;
    this.friend = friend;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;

  
  private FriendInfo friend;



  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public FriendInfo getFriend() {
    return friend;
  }

  public void setFriend(FriendInfo friend) {
    this.friend = friend;
  }



}
