package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ChatMessage implements Serializable {



  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private String id ;

  private int chatMessageType;

  private String messageContent;

  private Date messageDate;
  
  
  private String senderId;
  
  private String destinationId;
  
  private Date date;


  
  @Indexed
  private Set<SeenByDTO> seenBy = new HashSet<SeenByDTO>();



  @DBRef
  private DiamodoClient sender;




  public int getChatMessageType() {
    return chatMessageType;
  }


  public void setChatMessageType(int chatMessageType) {
    this.chatMessageType = chatMessageType;
  }


  public String getMessageContent() {
    return messageContent;
  }


  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }


  public Date getMessageDate() {
    return messageDate;
  }


  public void setMessageDate(Date messageDate) {
    this.messageDate = messageDate;
  }








  public DiamodoClient getSender() {
    return sender;
  }


  public void setSender(DiamodoClient sender) {
    this.sender = sender;
  }




  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getSenderId() {
    return senderId;
  }


  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }


  public String getDestinationId() {
    return destinationId;
  }


  public void setDestinationId(String destinationId) {
    this.destinationId = destinationId;
  }


  public Date getDate() {
    return date;
  }


  public void setDate(Date date) {
    this.date = date;
  }


  public Set<SeenByDTO> getSeenBy() {
    return seenBy;
  }


  public void setSeenBy(Set<SeenByDTO> seenBy) {
    this.seenBy = seenBy;
  }






 

}
