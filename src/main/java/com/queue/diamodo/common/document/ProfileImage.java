package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


public class ProfileImage implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  private String id = new ObjectId().toString();


  private String profilePictureName;

  private Date settingDate = new Date();



  public ProfileImage() {

  }


  public ProfileImage(String profilePictureName) {
    super();
    this.profilePictureName = profilePictureName;
    this.settingDate = new Date();
  }


  public ProfileImage(String profilePictureName, Date settingDate) {
    super();
    this.profilePictureName = profilePictureName;
    this.settingDate = settingDate;
  }



  public String getProfilePictureName() {
    return profilePictureName;
  }

  public void setProfilePictureName(String profilePictureName) {
    this.profilePictureName = profilePictureName;
  }

  public Date getSettingDate() {
    return settingDate;
  }

  public void setSettingDate(Date settingDate) {
    this.settingDate = settingDate;
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }

 

}
