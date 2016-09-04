package com.queue.diamodo.webservice.controller;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.common.velocity.DiamodoTemplateBean;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;
import com.queue.diamodo.webservice.common.DiamodoResponse;
import com.queue.diamodo.webservice.websocket.DiamodoEndPoint;

@RestController()
@RequestMapping("/diamodoClientController")
public class DiamodoClientController {

  Logger l = LogManager.getLogger(DiamodoClientController.class);

  @Autowired
  private DiamodoManagement diamodoManagement;
  
 @Autowired
 private DiamodoTemplateBean velocityBean;


  @Autowired
  private DiamodoClientDAO diamodoClientDAO;

  @RequestMapping(value = "/signup", produces = {"application/json"})
  public ResponseEntity<DiamodoResponse> signUp(Locale locale,
      @RequestBody(required = true) SignUpDTO signUpDTO) {

    try {

      UserInfoDTO diamodoClient = diamodoManagement.signUp(signUpDTO);

      UserInfoDTO userInfo = Utils.mapObjectToAnother(diamodoClient, UserInfoDTO.class);



      reigsterClientInWebsocketEndpoint(userInfo.getId(), userInfo.getUserToken());

      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(userInfo));

    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));
    }

  }


  private void reigsterClientInWebsocketEndpoint(String id, String uuid) {

    DiamodoEndPoint.reigsterClientInWebsocketEndpoint(id, uuid);

  }


  @RequestMapping(value = "/login", produces = {"application/json"})
  public ResponseEntity<DiamodoResponse> login(@RequestBody(required = true) LoginDTO loginDTO,
      Locale locale) {

    try {

      UserInfoDTO userInfo = diamodoManagement.login(loginDTO);


      reigsterClientInWebsocketEndpoint(userInfo.getId(), userInfo.getUserToken());

      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(userInfo));

    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));
    }

  }

  @RequestMapping(value = "/test", produces = {"application/json"})
  public List test(@RequestParam(name = "clientId") String clientId, @RequestParam(
      name = "conversationId") String conversationId) {
//    return diamodoManagement.getUnseenMessages(clientId, conversationId);
    
      return null;
  }




  @RequestMapping(value = "/resetDB", produces = {"application/json"})
  public void resetDB() {
    diamodoClientDAO.resetDB();
  }


}
