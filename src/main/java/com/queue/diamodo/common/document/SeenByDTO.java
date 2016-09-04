package com.queue.diamodo.common.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.queue.diamodo.common.utils.Utils;

public class SeenByDTO implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @DBRef
  private DiamodoClient diamodoClient;
  
  private Date seenDate =Utils.getTimeInUTC();

  public SeenByDTO() {
    super();
  }
  
  

  public SeenByDTO(DiamodoClient diamodoClient, Date seenDate) {
    super();
    this.diamodoClient = diamodoClient;
    this.seenDate = seenDate;
  }



  public SeenByDTO(DiamodoClient diamodoClient) {
    super();
    this.diamodoClient = diamodoClient;
  }



  public DiamodoClient getDiamodoClient() {
    return diamodoClient;
  }

  public void setDiamodoClient(DiamodoClient diamodoClient) {
    this.diamodoClient = diamodoClient;
  }

  public Date getSeenDate() {
    return seenDate;
  }

  public void setSeenDate(Date seenDate) {
    this.seenDate = seenDate;
  }
  
  
  

}
