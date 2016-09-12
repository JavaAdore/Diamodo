package com.queue.diamodo.webservice.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.webservice.common.DiamodoResponse;

@RestController()
@RequestMapping("/friendshipController")
public class FriendshipController {

  @Autowired
  private DiamodoConfigurations diamodoConfigurations;

  @Autowired
  private DiamodoManagement diamodoManagement;



  @RequestMapping(method = RequestMethod.POST, value = "/findFriends")
  public ResponseEntity<DiamodoResponse> findFriends(@RequestHeader(name = "clientId",
      required = true) String searcherId,
      @RequestBody FriendsSearchCriteriaDTO friendsSearchCriteriaDTO, Locale locale) {
    try {
      List<FriendSearchResult> result =
          diamodoManagement.findFriends(searcherId, friendsSearchCriteriaDTO);
      if (!result.isEmpty()) {
        return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(result));
      } else {
        return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(
            DiamodoResourceBundleUtils.NO_RESULT_FOUND_MESSAGE, locale));
      }
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/addFriend/{friendId}")
  public ResponseEntity<DiamodoResponse> sendFriendRequest(@RequestHeader(name = "clientId",
      required = true) String clientId, @PathVariable String friendId, Locale locale) {
    try {

      Friendship friendShip = diamodoManagement.sendFriendRequest(clientId, friendId);
    
      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(
          DiamodoResourceBundleUtils.FRIEND_REQUEST_SENT_SUCCESSFULLY_MESSAGE, friendShip,locale));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }
  }


  @RequestMapping(method = RequestMethod.GET, value = "/acceptFriendship/{friendshipId}")
  public ResponseEntity<DiamodoResponse> acceptFriendship(@RequestHeader(name = "clientId",
      required = true) String clientId, @PathVariable String friendshipId, Locale locale) {
    try {
      
      System.out.println("accept friend request ");
      System.out.println("friendship id is " + friendshipId);
      System.out.println("client id  is " + clientId );
      
      diamodoManagement.acceptFriendShip(clientId, friendshipId);

      return ResponseEntity.ok(DiamodoResponse
          .prepareSuccessResponse(DiamodoResourceBundleUtils.ACCEPT_FRIEND_REQUEST_MESSAGE,locale));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }
  }



  @RequestMapping(method = RequestMethod.GET, value = "/rejectFriendship/{friendshipId}")
  public ResponseEntity<DiamodoResponse> rejectFriendship(@RequestHeader(name = "clientId",
      required = true) String clientId, @PathVariable String friendshipId, Locale locale) {
    try {

      System.out.println("reject friend request ");
      System.out.println("friendship id is " + friendshipId);
      System.out.println("client id  is " + clientId );
      
      
      diamodoManagement.rejectFriendship(clientId, friendshipId);

      return ResponseEntity.ok(DiamodoResponse
          .prepareSuccessResponse(DiamodoResourceBundleUtils.REJECT_FRIEND_REQUEST_SUCCESS_MESSAGE));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }
  }



  @RequestMapping(method = RequestMethod.GET, value = "/unfriend/{friendshipId}")
  public ResponseEntity<DiamodoResponse> unfriend(
      @RequestHeader(name = "clientId", required = true) String clientId,
      @PathVariable String friendshipId, Locale locale) {
    try {

      diamodoManagement.rejectFriendship(clientId, friendshipId);

      return ResponseEntity.ok(DiamodoResponse
          .prepareSuccessResponse(DiamodoResourceBundleUtils.REJECT_FRIEND_REQUEST_SUCCESS_MESSAGE));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));

    }
  }


  @RequestMapping(method = RequestMethod.POST, value = "/getMyFriendShips")
  public ResponseEntity<DiamodoResponse> getMyFriends(@RequestHeader(name = "clientId",
      required = true) String clientId, @RequestBody PagingDTO pagingDTO, Locale locale) {
    try {
      List<FriendRepresentationDTO> result = diamodoManagement.getMyFriends(clientId, pagingDTO);
      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(result));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));
    }

  }

  
  @RequestMapping(method = RequestMethod.POST, value = "/getMyFriendshipRequests")
  public ResponseEntity<DiamodoResponse> getMyFriendshipRequests(@RequestHeader(name = "clientId",
      required = true) String clientId, @RequestBody PagingDTO pagingDTO, Locale locale) {
    try {
      List<FriendRepresentationDTO> result =
          diamodoManagement.getMyFriendshipRequests(clientId, pagingDTO);
      return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(result));
    } catch (DiamodoCheckedException ex) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareFailureResponse(ex, locale));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
          DiamodoResponse.prepareBackendErrorResponse(locale));
    }


  }

}
