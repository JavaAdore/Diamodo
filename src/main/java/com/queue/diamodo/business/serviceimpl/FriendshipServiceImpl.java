package com.queue.diamodo.business.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.service.FriendshipService;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dao.FriendshipDAO;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.webservice.websocket.DiamodoEndPoint;

@Service
public class FriendshipServiceImpl extends CommonService implements FriendshipService {



  @Autowired
  private FriendshipDAO friendshipDAO;

  @Autowired
  private DiamodoClientDAO diamodoClientDAO;


  @Autowired
  private DiamodoConfigurations diamodoConfigurations;

  @Override
  public List<FriendSearchResult> findFriends(String searcherId,
      FriendsSearchCriteriaDTO friendsSearchCriteriaDTO) throws DiamodoCheckedException {


    validateFriendsSearchCriteriaDTO(friendsSearchCriteriaDTO);

    int numberOfResultNeeded =
        getProperNumberOfNeededResult(friendsSearchCriteriaDTO.getNumberOfResultNeeded());


    int numberOfResultToSkip =
        getProperNumberOfResultToSkip(friendsSearchCriteriaDTO.getNumberOfResultsToSkip());

    List<FriendSearchResult> searchingResult = null;

    if (Utils.isEmail(friendsSearchCriteriaDTO.getSearchInput())) {
      searchingResult =
          friendshipDAO.findFriendsByEmail(searcherId, friendsSearchCriteriaDTO.getSearchInput(),
              numberOfResultToSkip, numberOfResultNeeded);
    } else {
      searchingResult =
          friendshipDAO
              .findFriendsByUserName(searcherId, friendsSearchCriteriaDTO.getSearchInput(),
                  numberOfResultToSkip, numberOfResultNeeded);
    }



    execludeSearcherBlockedUsers(searcherId, searchingResult);

    updateAlreadyInRelationResultStatus(searcherId);


    return searchingResult;
  }

  private void updateAlreadyInRelationResultStatus(String searcherId) {
    // TODO Auto-generated method stub

  }

  private void execludeSearcherBlockedUsers(String searcherId,
      List<FriendSearchResult> searchingResult) {
    // TODO Auto-generated method stub

  }

  private int getProperNumberOfResultToSkip(int numberOfResultsToSkip) {
    if (numberOfResultsToSkip > 0
        && numberOfResultsToSkip <= diamodoConfigurations.MAX_NUMBER_OF_TOTAL_SEARCH_RESULT_FOR_USER) {
      return numberOfResultsToSkip;
    }
    return 0;
  }

  private int getProperNumberOfNeededResult(int numberOfResultNeeded) {
    if (numberOfResultNeeded > 0
        && numberOfResultNeeded <= diamodoConfigurations.MAX_NUMBER_OF_RESULT_ALLOWED_PER_FRIENDS_SEARCH) {
      return numberOfResultNeeded;
    }
    return diamodoConfigurations.DEFAULT_NUMBER_OF_RESULT_NEEDED_IN_SEARCH_RESULT;
  }

  private void validateFriendsSearchCriteriaDTO(FriendsSearchCriteriaDTO friendsSearchCriteriaDTO)
      throws DiamodoCheckedException {

    if (Utils.isEmpty(friendsSearchCriteriaDTO.getSearchInput())) {
      throwDiamodException(DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_KEY);
    }

    if (friendsSearchCriteriaDTO.getSearchInput().contains("@")
        && !Utils.isValidEmailAddress(friendsSearchCriteriaDTO.getSearchInput())) {
      throwDiamodException(DiamodoResourceBundleUtils.INVALID_EMAIL_ADDRESS_CODE,
          DiamodoResourceBundleUtils.INVALID_EMAIL_ADDRESS_KEY);
    }

  }

  @Override
  public Friendship sendFriendRequest(String clientId, String friendId)
      throws DiamodoCheckedException {

    validateSender(clientId);

    validateIdentity(clientId, friendId);

    validateFriend(friendId);

    validateSendingRequestBlockingStatus(clientId, friendId);

    validateFriendShip(clientId, friendId);

    Friendship friendship = saveFriendShip(clientId, friendId);
    return friendship;


  }

  private void validateIdentity(String clientId, String friendId) throws DiamodoCheckedException {
    if (clientId.equals(friendId)) {
      throwDiamodException(DiamodoResourceBundleUtils.YOU_CANNOT_SENT_REQUEST_TO_YOUR_SELF_CODE,
          DiamodoResourceBundleUtils.YOU_CANNOT_SENT_REQUEST_TO_YOUR_SELF_KEY);
    }

  }

  private Friendship saveFriendShip(String clientId, String friendId) {

    Friendship friendship = new Friendship();
    friendship.setFriend(new DiamodoClient(friendId));
    friendship.setFriendShipRequestSender(clientId);
    friendship.setFriendShipStatus(Friendship.FRIEND_SHIP_STATUS_SENT);

    friendshipDAO.addFriendshipToDiamodoClient(clientId, friendship);

    friendship.setFriend(new DiamodoClient(clientId));
    friendship.setFriendShipStatus(Friendship.FRIEND_SHIP_STATUS_RECIEVED);

    friendship = friendshipDAO.addFriendshipToDiamodoClient(friendId, friendship);
    return friendship;

  }

  private void validateSendingRequestBlockingStatus(String clientId, String friendId)
      throws DiamodoCheckedException {

    DiamodoClient diamodoClient = diamodoClientDAO.getBlockingInfoData(clientId);

    if (diamodoClient.getAccountsBlockMe().contains(friendId)
        || diamodoClient.getAccountsIBlock().contains(clientId)) {
      throwDiamodException(
          DiamodoResourceBundleUtils.SORRY_YOU_CANNOT_SENT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_CODE,
          DiamodoResourceBundleUtils.SORRY_YOU_CANNOT_SENT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY);
    }

  }


  private void validateAcceptingRequestBlockingStatus(String clientId, String friendId)
      throws DiamodoCheckedException {

    DiamodoClient diamodoClient = diamodoClientDAO.getBlockingInfoData(clientId);

    if (diamodoClient.getAccountsBlockMe().contains(friendId)
        || diamodoClient.getAccountsIBlock().contains(clientId)) {
      throwDiamodException(
          DiamodoResourceBundleUtils.SORRY_YOU_CANNOT_ACCEPT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_CODE,
          DiamodoResourceBundleUtils.SORRY_YOU_CANNOT_ACCEPT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY);
    }

  }

  private void validateFriendShip(String clientId, String friendId) throws DiamodoCheckedException {

    Friendship friendship = friendshipDAO.getFriendshipBySenderOrReciever(clientId, friendId);

    if (Utils.isNotEmpty(friendship)) {

      if (friendship.getFriendShipRequestSender().equals(clientId)) {
        throwDiamodException(DiamodoResourceBundleUtils.FRIEND_SHIP_REQUEST_ALREADY_SENT_CODE,
            DiamodoResourceBundleUtils.FRIEND_SHIP_REQUEST_ALREADY_SENT_KEY);
      } else {
        throwDiamodException(
            DiamodoResourceBundleUtils.FRIEND_HAS_SENT_YOU_FRIENDSHIP_REQUEST_YOU_CAN_ACCEPT_IT_CODE,
            DiamodoResourceBundleUtils.FRIEND_HAS_SENT_YOU_FRIENDSHIP_REQUEST_YOU_CAN_ACCEPT_IT_KEY);
      }
    }

  }


  @Override
  public String acceptFriendShip(String clientId, String friendshipId)
      throws DiamodoCheckedException {

    validateFriendship(friendshipId);

    Friendship friendship = friendshipDAO.findFriendsById(clientId, friendshipId);

    validateFrienshipOwnerShip(friendship);

    validateAcceptingFriendShipStatus(friendship);

    validateAcceptingRequestBlockingStatus(clientId, friendship.getFriendId());

    friendshipDAO.updateFriendshipStatus(clientId, friendshipId,
        Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND);

    friendshipDAO.updateFriendshipStatus(friendship.getFriendShipRequestSender(), friendshipId,
        Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND);

    return null;

  }


  private void validateAcceptingFriendShipStatus(Friendship friendship)
      throws DiamodoCheckedException {
    if (friendship.getFriendShipStatus() == Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND) {
      throwDiamodException(DiamodoResourceBundleUtils.YOU_ARE_ALREADY_FRIENDS_CODE,
          DiamodoResourceBundleUtils.INVALID_ACTION_KEY);
    }


    if (friendship.getFriendShipStatus() != Friendship.FRIEND_SHIP_STATUS_RECIEVED) {
      throwDiamodException(DiamodoResourceBundleUtils.INVALID_ACTION_CODE,
          DiamodoResourceBundleUtils.INVALID_ACTION_KEY);
    }


  }

  private void validateFrienshipOwnerShip(Friendship friendship) throws DiamodoCheckedException {
    if (Utils.isEmpty(friendship)) {
      throwDiamodException(DiamodoResourceBundleUtils.FRIEND_SHIP_REQUEST_IS_NO_LONGER_EXIST_CODE,
          DiamodoResourceBundleUtils.FRIEND_SHIP_REQUEST_IS_NO_LONGER_EXIST_KEY);
    }

  }

  private void validateFriendship(String friendshipId) throws DiamodoCheckedException {
    if (Utils.isEmpty(friendshipId)) {
      throwDiamodException(DiamodoResourceBundleUtils.FRIEND_SHIP_ID_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.FRIEND_SHIP_ID_IS_REQUIRED_KEY);
    }

  }

  @Override
  public String rejectFriendship(String clientId, String friendshipId)
      throws DiamodoCheckedException {

    validateFriendship(friendshipId);

    Friendship friendship = friendshipDAO.findFriendsById(clientId, friendshipId);

    validateFrienshipOwnerShip(friendship);

    validateAcceptingFriendShipStatus(friendship);

    validateAcceptingRequestBlockingStatus(clientId, friendship.getFriendId());

    // friendshipDAO.deleteFriendShip(clientId, friendshipId);

    friendshipDAO.updateFriendshipStatus(clientId, friendship.getId(),
        Friendship.FRIEND_SHIP_STATUS_HAS_BEEN_REJECTED);

    // friendshipDAO.deleteFriendShip(friendship.getFriendId(), friendshipId);

    friendshipDAO.updateFriendshipStatus(friendship.getFriendShipRequestSender(),
        friendship.getId(), Friendship.FRIEND_SHIP_STATUS_HAS_BEEN_REJECTED);

    return friendshipId;
  }

  @Override
  public List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {

    validateClientExistance(clientId);

    List<FriendRepresentationDTO> friendship =
        friendshipDAO.getFriendRepresentation(clientId,
            Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND, pagingDTO.getNumberOfResultNeeded(),
            pagingDTO.getNumberOfResultsToSkip());



    return friendship;
  }

  @Override
  public List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {

    validateClientExistance(clientId);

    return friendshipDAO.getFriendRepresentation(clientId, Friendship.FRIEND_SHIP_STATUS_RECIEVED,
        pagingDTO.getNumberOfResultNeeded(), pagingDTO.getNumberOfResultsToSkip());
  }


}
