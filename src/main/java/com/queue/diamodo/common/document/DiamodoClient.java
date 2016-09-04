package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.queue.diamodo.common.document.base.BaseDocument;
import com.queue.diamodo.common.utils.Utils;


@Document
public class DiamodoClient extends BaseDocument implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public final static int INITIAL_CAPACITY_OF_PROFILE_PICTURES_LIST = 1;


  public DiamodoClient() {
    super();
  }



  public DiamodoClient(String id) {
    super();
    this.id = id;
  }



  @Id
  private String id;

  private String firstName;

  private String lastName;

  @Indexed(unique = true, background = true, sparse = true)
  private String userName;

  @Indexed(unique = true, background = true, sparse = true)
  private String email;

  private String password;

  private Date registrationDate = new Date();


  private ProfileImage currentProfileImage;
  
  
  private ClientDevice clientDevice;
  
  
  private String userToken;
  

  private List<ProfileImage> profilePicturesHistory = new ArrayList<ProfileImage>(0);

  @Indexed
  private List<String> accountsIBlock = new ArrayList<String>();

  @Indexed
  private List<String> accountsBlockMe = new ArrayList<String>();

  @Indexed
  @DBRef(lazy=true)
  private List<Friendship> friendships = new ArrayList<Friendship>();

  
  private List<Friendship> friendshipHistory = new ArrayList<Friendship>();
  
  @Indexed
  @DBRef(lazy=true)
  private List<Conversation> memberConversations = new ArrayList<Conversation>();
  
  
  public String getId() {
    return id;
  }

  public void setId(String id) { 
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }



  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }






  public List<ProfileImage> getProfilePicturesHistory() {
    return profilePicturesHistory;
  }

  public void setProfilePicturesHistory(List<ProfileImage> profilePicturesHistory) {
    this.profilePicturesHistory = profilePicturesHistory;
  }

  public ProfileImage getCurrentProfileImage() {
    return currentProfileImage;
  }

  public void setCurrentProfileImage(ProfileImage currentProfileImage) {
    this.currentProfileImage = currentProfileImage;
  }



  public List<String> getAccountsIBlock() {
    return accountsIBlock;
  }

  public void setAccountsIBlock(List<String> accountsIBlock) {
    this.accountsIBlock = accountsIBlock;
  }

  public List<String> getAccountsBlockMe() {
    return accountsBlockMe;
  }

  public void setAccountsBlockMe(List<String> accountsBlockMe) {
    this.accountsBlockMe = accountsBlockMe;
  }

  public List<Friendship> getFriendships() {
    return friendships;
  }

  public void setFriendships(List<Friendship> friendships) {
    this.friendships = friendships;
  }



  public List<Friendship> getFriendshipHistory() {
    return friendshipHistory;
  }

  public void setFriendshipHistory(List<Friendship> friendshipHistory) {
    this.friendshipHistory = friendshipHistory;
  }

  @Override
  public String toString() {
    return "DiamodoClient [id=" + id +", userName=" + userName + ", email=" + email + "]";
  }



  public List<Conversation> getMemberConversations() {
    return memberConversations;
  }



  public void setMemberConversations(List<Conversation> memberConversations) {
    this.memberConversations = memberConversations;
  }



  public ClientDevice getClientDevice() {
    return clientDevice;
  }



  public void setClientDevice(ClientDevice clientDevice) {
    this.clientDevice = clientDevice;
  }



  public String getUserToken() {
    return userToken;
  }



  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }


  public void assignRandomToken()
  {
    userToken = Utils.generateRandomToken();
  }







}
