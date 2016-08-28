package com.queue.diamodo.webservice.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;
import com.queue.diamodo.webservice.common.DiamodoResponse;

@RestController()
@RequestMapping("/diamodoClientController")
public class DiamodoClientController {

  @Autowired
  private DiamodoManagement diamodoManagement;


  @Autowired
  private DiamodoClientDAO diamodoClientDAO;

  @RequestMapping(value = "/signup", produces = {"application/json"})
  public ResponseEntity<DiamodoResponse> signUp(Locale locale,
      @RequestBody(required = true) SignUpDTO signUpDTO) {

    try {

      DiamodoClient diamodoClient = diamodoManagement.signUp(signUpDTO);

      UserInfoDTO userInfo = Utils.mapObjectToAnother(diamodoClient, UserInfoDTO.class);

      String uuid = Utils.generateRandomToken();

      userInfo.setUserToken(uuid);

      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(userInfo));

    } catch (DiamodoCheckedException ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));
    }

  }


  @RequestMapping(value = "/login", produces = {"application/json"})
  public ResponseEntity<DiamodoResponse> login(@RequestBody(required = true) LoginDTO loginDTO, Locale locale) {

    try {

      DiamodoClient diamodoClient = diamodoManagement.login(loginDTO);

      UserInfoDTO userInfo = Utils.mapObjectToAnother(diamodoClient, UserInfoDTO.class);

      String uuid = Utils.generateRandomToken();

      userInfo.setUserToken(uuid);

      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(userInfo));

    } catch (DiamodoCheckedException ex) {
       return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
       
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(DiamodoResponse.prepareBackendErrorResponse(locale));
    }

  }

  @RequestMapping(value = "/test", produces = {"application/json"})
  public List test() {
    return diamodoClientDAO.test();
  }



  public static void main(String[] args) {

    // LoginDTO signUpDTO = new LoginDTO();
    // signUpDTO.setLoginAttribute("mahmoud@gmail.com");
    //
    // signUpDTO.setPassword("123456");
    // Gson gson = new Gson();
    // System.out.println(gson.toJson(signUpDTO));

    FriendsSearchCriteriaDTO friendsSearchCriteriaDTO = new FriendsSearchCriteriaDTO();
    friendsSearchCriteriaDTO.setNumberOfResultNeeded(10);
    friendsSearchCriteriaDTO.setNumberOfResultsToSkip(20);
    friendsSearchCriteriaDTO.setSearchInput("Mohamed@gmail.com");

    // ClientImageHolder clientImageHolder = new ClientImageHolder();
    // clientImageHolder.setBase64Image("ccczxczxcxz");
    Gson gson = new Gson();
    System.out.println(gson.toJson(friendsSearchCriteriaDTO));

  }

  @RequestMapping(value = "/resetDB", produces = {"application/json"})
  public void resetDB()
  {
    diamodoClientDAO.resetDB();
  }


}
