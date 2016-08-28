package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;


   
public class Friendship implements Serializable {


  public final static int FRIEND_SHIP_STATUS_SENT = 1;
  public final static int FRIEND_SHIP_STATUS_RECIEVED = 2;
  public final static int FRIEND_SHIP_STATUS_ALREADY_FRIEND = 3;
  public final static int FRIEND_SHIP_STATUS_NOT_FRIEND_YET = 4;
  public final static int FRIEND_SHIP_STATUS_HAS_BEEN_REJECTED = 5;

  private static final long serialVersionUID = 1L;


  private String id = new ObjectId().toString();

  
  @JsonIgnore
  @DBRef
  private DiamodoClient friend;


  @Indexed
  private String friendShipRequestSender;

  private Date friendshipDate = new Date();

  private int friendShipStatus;

  private boolean seen;



  public String getFriendShipRequestSender() {
    return friendShipRequestSender;
  }

  public void setFriendShipRequestSender(String friendShipRequestSender) {
    this.friendShipRequestSender = friendShipRequestSender;
  }

  public int getFriendShipStatus() {
    return friendShipStatus;
  }

  public void setFriendShipStatus(int friendShipStatus) {
    this.friendShipStatus = friendShipStatus;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }

  public Date getFriendshipDate() {
    return friendshipDate;
  }

  public void setFriendshipDate(Date friendshipDate) {
    this.friendshipDate = friendshipDate;
  }

  public DiamodoClient getFriend() {
    return friend;
  }



  public String getFriendId() {
    return friend != null ? friend.getId() : null;
  }

  public void setFriend(DiamodoClient friend) {
    this.friend = friend;
  }
  
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    Friendship other = (Friendship) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  
  

}
