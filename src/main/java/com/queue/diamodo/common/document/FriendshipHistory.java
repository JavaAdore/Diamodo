package com.queue.diamodo.common.document;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class FriendshipHistory {
  
  @DBRef(lazy=true)
  private DiamodoClient diamodoClient;
  
  public DiamodoClient getDiamodoClient() {
    return diamodoClient;
  }

  public void setDiamodoClient(DiamodoClient diamodoClient) {
    this.diamodoClient = diamodoClient;
  }

  private Date date = new Date();
  
  private String action;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @Override
  public String toString() {
    return "FrienshipHistory [diamodoClient=" + diamodoClient + ", date=" + date + ", action="
        + action + "]";
  }
  
  
}
