package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class FriendsSearchCriteriaDTO extends PagingDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;



  private String searchInput;



  public String getSearchInput() {
    return searchInput;
  }

  public void setSearchInput(String searchInput) {
    this.searchInput = searchInput;
  }

  @Override
  public String toString() {
    return "FriendsSearchCriteriaDTO [searchInput=" + searchInput + "," + super.toString() + "]";
  }



}
