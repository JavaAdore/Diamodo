package com.queue.diamodo.webservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.ClientProfileImage;
import com.queue.diamodo.webservice.common.DiamodoResponse;

@RestController()
@RequestMapping("/diamodoProfileController")
public class DiamodoProfileController {

  @Autowired
  private DiamodoConfigurations diamodoConfigurations;

  @Autowired
  private DiamodoManagement diamodoManagement;



  // @RequestMapping(method = RequestMethod.POST, value = "/updateProfilePicture")
  // public DiamodoResponse handleFileUpload(@RequestHeader(name="clientId" , required=true) String
  // clientId,
  // @RequestHeader(name="userToken" , required=true) String userToken, @RequestParam("file")
  // MultipartFile file,
  // Locale locale) {
  //
  // if (!file.isEmpty()) {
  //
  // try {
  // String fullFileName = clientId+"_"+new Random().nextLong() ;;
  // fullFileName = Utils.fixFileName(fullFileName );
  // Files.copy(file.getInputStream(), Paths.get(
  // diamodoConfigurations.DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION, fullFileName));
  //
  // diamodoManagement.updateClientProfileImagePath(clientId, fullFileName);
  //
  // return DiamodoResponse.prepareSuccessResponse(new ClientProfileImage(fullFileName));
  //
  // } catch (DiamodoCheckedException ex) {
  // return DiamodoResponse.prepareFailureResponse(ex, locale);
  // } catch (Exception ex) {
  // return DiamodoResponse.prepareBackendErrorResponse(locale);
  // }
  // } else {
  // return DiamodoResponse.prepareFailureResponse(
  // DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_CODE,
  // DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_KEY, locale);
  // }
  //
  //
  // }
  //
  //
  @RequestMapping(method = RequestMethod.POST, value = "/updateProfilePicture")
  public ResponseEntity<DiamodoResponse> updateProfilePicture(@RequestHeader(name = "clientId",
      required = true) String clientId,
      @RequestHeader(name = "userToken", required = true) String userToken,
      @RequestBody ClientImageHolder clientImageHolder, Locale locale) {


    try {

      String fullFileName =
          diamodoManagement.updateClientProfileImagePath(clientId, clientImageHolder);

      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(new ClientProfileImage(
          fullFileName)));

    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }


  }



  @RequestMapping("/profileImage/{fileName}")
  public HttpEntity<byte[]> getProfileImage(@PathVariable String fileName) {

    try {
      byte[] image;

      image =
          FileCopyUtils.copyToByteArray(new File(
              diamodoConfigurations.DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION + File.separator
                  + fileName));


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
