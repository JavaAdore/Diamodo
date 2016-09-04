package com.queue.diamodo.dataaccess.dto;

import java.io.Serializable;

public class PagingDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int numberOfResultsToSkip;

  private int numberOfResultNeeded;

  public int getNumberOfResultsToSkip() {
    return numberOfResultsToSkip;
  }

  public void setNumberOfResultsToSkip(int numberOfResultsToSkip) {
    this.numberOfResultsToSkip = numberOfResultsToSkip;
  }

  public int getNumberOfResultNeeded() {
    return numberOfResultNeeded;
  }

  public void setNumberOfResultNeeded(int numberOfResultNeeded) {
    this.numberOfResultNeeded = numberOfResultNeeded;
  }

  @Override
  public String toString() {
    return "PagingDTO [numberOfResultsToSkip=" + numberOfResultsToSkip + ", numberOfResultNeeded="
        + numberOfResultNeeded + "]";
  }

}
