package com.queue.diamodo.dataaccess.dto;

import java.util.Date;

public class UserLocationDTO {

  private String userId;

  private Double lat;

  private Double lon;

  private Date date;



  public UserLocationDTO() {}

  public UserLocationDTO(Date date, Double lat, Double lon) {
    super();
    this.date = date;
    this.lat = lat;
    this.lon = lon;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }


}
