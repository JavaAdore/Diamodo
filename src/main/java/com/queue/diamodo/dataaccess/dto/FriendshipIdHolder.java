package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class FriendshipIdHolder implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String friendshipId;



  public FriendshipIdHolder() {
    super();
  }

  public FriendshipIdHolder(String friendshipId) {
    super();
    this.friendshipId = friendshipId;
  }

  public String getFriendshipId() {
    return friendshipId;
  }

  public void setFriendshipId(String friendshipId) {
    this.friendshipId = friendshipId;
  }



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((friendshipId == null) ? 0 : friendshipId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FriendshipIdHolder other = (FriendshipIdHolder) obj;
    if (friendshipId == null) {
      if (other.friendshipId != null)
        return false;
    } else if (!friendshipId.equals(other.friendshipId))
      return false;
    return true;
  }



}
