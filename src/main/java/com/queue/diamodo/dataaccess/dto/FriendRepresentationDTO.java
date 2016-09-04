package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class FriendRepresentationDTO implements Serializable {

  
  
  public FriendRepresentationDTO()
  {
    
  }
  
  




  public FriendRepresentationDTO(String id, ClientInfo friend) {
    super();
    this.id = id;
    this.friend = friend;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;

  
  private ClientInfo friend;



  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ClientInfo getFriend() {
    return friend;
  }

  public void setFriend(ClientInfo friend) {
    this.friend = friend;
  }


  @Override
  public String toString() {
    return "FriendRepresentationDTO [id=" + id + ", friend=" + friend + "]";
  }



}
