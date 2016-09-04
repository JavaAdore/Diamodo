package com.queue.diamodo.webservice.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.common.config.DiamodoConfigurations;

@RestController()
@RequestMapping("/download")
public class DownloadMediaController {

  
  @Autowired
  private DiamodoConfigurations diamodoConfigurations;
  
  
  @RequestMapping( value = "/downloadSocketChatImage/{clientId}/{destinationId}/{fileName}")
  public HttpEntity<byte[]> downloadSocketChatImage(@PathVariable String fileName) {

    try {
      byte[] image;

      image =
          FileCopyUtils.copyToByteArray(new File(
              diamodoConfigurations.DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CHAT_MESSAGE_FILES_FOLDER_LOCATION + File.separator
                  + fileName + ".png"));

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG);
      headers.setContentLength(image.length);

      return new HttpEntity<byte[]>(image, headers);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
