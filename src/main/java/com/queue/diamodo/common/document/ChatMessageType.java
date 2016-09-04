package com.queue.diamodo.common.document;

import java.io.Serializable;

public interface ChatMessageType extends Serializable {


  /**
   * 
   */

  public final static int MESSAEG_TYPE_TEXT_MESSAGE = 1;

  public final static int MESSAEG_TYPE_BUZZ_MESSAGE = 2;

  public final static int MESSAEG_TYPE_BASE64_IMAGE_MESSAGE = 3;



}
