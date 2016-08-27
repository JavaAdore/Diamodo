package com.queue.diamodo.common.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.queue.diamodo.common.document.base.BaseDocument;

@Document
public class DiamodoClient extends BaseDocument {

  public final static int INITIAL_CAPACITY_OF_HOBBIES_LIST = 1;

  @Id
  private String id;

  private String firstName;

  private String lastName;

  @Indexed(unique = true, background = true, sparse = true)
  private String userName;

  @Indexed(unique = true, background = true, sparse = true)
  private String email;

  private String password;

  private List<String> hobbies = new ArrayList<String>(INITIAL_CAPACITY_OF_HOBBIES_LIST);



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

  public List<String> getHobbies() {
    return hobbies;
  }

  public void setHobbies(List<String> hobbies) {
    this.hobbies = hobbies;
  }

  @Override
  public String toString() {
    return "DiamodoClient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
        + ", userName=" + userName + ", email=" + email + "]";
  }



}
