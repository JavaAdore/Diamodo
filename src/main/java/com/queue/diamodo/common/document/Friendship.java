package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Document
public class Friendship implements Serializable {


  public final static int FRIEND_SHIP_STATUS_SENT = 1;
  public final static int FRIEND_SHIP_STATUS_RECIEVED = 2;
  public final static int FRIEND_SHIP_STATUS_ALREADY_FRIEND = 3;
  public final static int FRIEND_SHIP_STATUS_NOT_FRIEND_YET = 4;
  public final static int FRIEND_SHIP_STATUS_HAS_BEEN_REJECTED = 5;
  public final static int FRIEND_SHIP_STATUS_HAS_BEEN_DELETED = 6;


  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  @JsonIgnore
  @DBRef(lazy = true)
  private DiamodoClient partOne;



  @JsonIgnore
  @DBRef(lazy = true)
  private DiamodoClient partTwo;



  @Indexed
  @JsonIgnore
  @DBRef(lazy = true)
  private DiamodoClient friendShipRequestSender;

  private Date friendshipDate = new Date();

  @JsonIgnore
  private int partOneFrienshipStatus;
  @JsonIgnore
  private int partTwoFrienshipStatus;

  private boolean seen;

  @JsonIgnore
  private List<FriendshipHistory> friendshipHistory;
   

  

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DiamodoClient getPartOne() {
    return partOne;
  }

  public void setPartOne(DiamodoClient partOne) {
    this.partOne = partOne;
  }

  public DiamodoClient getPartTwo() {
    return partTwo;
  }

  public void setPartTwo(DiamodoClient partTwo) {
    this.partTwo = partTwo;
  }

  public DiamodoClient getFriendShipRequestSender() {
    return friendShipRequestSender;
  }

  public void setFriendShipRequestSender(DiamodoClient friendShipRequestSender) {
    this.friendShipRequestSender = friendShipRequestSender;
  }

  public Date getFriendshipDate() {
    return friendshipDate;
  }

  public void setFriendshipDate(Date friendshipDate) {
    this.friendshipDate = friendshipDate;
  }

  public int getPartOneFrienshipStatus() {
    return partOneFrienshipStatus;
  }

  public void setPartOneFrienshipStatus(int partOneFrienshipStatus) {
    this.partOneFrienshipStatus = partOneFrienshipStatus;
  }



  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }

  
  public String friendId()
  {
    return (partTwo!=null)? partTwo.getId():""; 
  }
  
  
  

  public int getPartTwoFrienshipStatus() {
    return partTwoFrienshipStatus;
  }

  public void setPartTwoFrienshipStatus(int partTwoFrienshipStatus) {
    this.partTwoFrienshipStatus = partTwoFrienshipStatus;
  }

  public List<FriendshipHistory> getFriendshipHistory() {
    return friendshipHistory;
  }

  public void setFriendshipHistory(List<FriendshipHistory> friendshipHistory) {
    this.friendshipHistory = friendshipHistory;
  }
}
