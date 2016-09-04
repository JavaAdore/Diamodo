package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Conversation implements Serializable {


  public Conversation(String id) {
    super();
    this.id = id;
  }


  public Conversation() {
    super();
  }


  /**
   *  
   */

  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  private Date initiationDate;

  private String conversationName;
  
  private String conversationImage;
  
  private boolean isGroupChat;
  
  private boolean isPublicConversation;

  @Indexed
  @DBRef(lazy = true)
  private DiamodoClient conversationInitiator;


  @DBRef(lazy = true)
  private Set<DiamodoClient> conversationMembers = new HashSet<DiamodoClient>();

  @Indexed
  private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

  @DBRef(lazy = true)
  private Set<DiamodoClient> conversationAdministrators = new HashSet<DiamodoClient>();

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }



  public DiamodoClient getConversationInitiator() {
    return conversationInitiator;
  }


  public void setConversationInitiator(DiamodoClient conversationInitiator) {
    this.conversationInitiator = conversationInitiator;
  }


  public List<ChatMessage> getChatMessages() {
    return chatMessages;
  }


  public void setChatMessages(List<ChatMessage> chatMessages) {
    this.chatMessages = chatMessages;
  }


  public Set<DiamodoClient> getConversationMembers() {
    return conversationMembers;
  }


  public void setConversationMembers(Set<DiamodoClient> conversationMembers) {
    this.conversationMembers = conversationMembers;
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
    Conversation other = (Conversation) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }


  public Date getInitiationDate() {
    return initiationDate;
  }


  public void setInitiationDate(Date initiationDate) {
    this.initiationDate = initiationDate;
  }


  public String getConversationName() {
    return conversationName;
  }


  public void setConversationName(String conversationName) {
    this.conversationName = conversationName;
  }


  public Set<DiamodoClient> getConversationAdministrators() {
    return conversationAdministrators;
  }


  public void setConversationAdministrators(Set<DiamodoClient> conversationAdministrators) {
    this.conversationAdministrators = conversationAdministrators;
  }


  public String getConversationImage() {
    return conversationImage;
  }


  public void setConversationImage(String conversationImage) {
    this.conversationImage = conversationImage;
  }


  public boolean isGroupChat() {
    return isGroupChat;
  }


  public void setGroupChat(boolean isGroupChat) {
    this.isGroupChat = isGroupChat;
  }


  public boolean isPublicConversation() {
    return isPublicConversation;
  }


  public void setPublicConversation(boolean isPublicConversation) {
    this.isPublicConversation = isPublicConversation;
  }



}
