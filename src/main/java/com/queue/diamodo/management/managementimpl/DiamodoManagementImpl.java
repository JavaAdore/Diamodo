package com.queue.diamodo.management.managementimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.business.service.DiamodoClientService;
import com.queue.diamodo.business.service.FriendshipService;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;

@Service
public class DiamodoManagementImpl implements DiamodoManagement {


  @Autowired
  private DiamodoClientService diamodoClientService;

  @Autowired
  private FriendshipService friendshipService;

  @Override
  public DiamodoClient signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException {

    return diamodoClientService.signUp(signUpDTO);
  }

  @Override
  public DiamodoClient login(LoginDTO loginDTO) throws DiamodoCheckedException {
    return diamodoClientService.login(loginDTO);
  }

  @Override
  public void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException {

    diamodoClientService.updateClientProfileImagePath(clientId, fullFileName);
  }

  @Override
  public List<FriendSearchResult> findFriends(String searcherId,
      FriendsSearchCriteriaDTO friendsSearchCriteriaDTO) throws DiamodoCheckedException {

    return friendshipService.findFriends(searcherId, friendsSearchCriteriaDTO);

  }

  @Override
  public Friendship sendFriendRequest(String clientId, String friendId)
      throws DiamodoCheckedException {
    Friendship friendShip = friendshipService.sendFriendRequest(clientId, friendId);
    notifyFriendForFriendship(friendId, friendShip);
    return friendShip;
  }

  private void notifyFriendForFriendship(String friendId, Friendship friendShip) {

  }

  @Override
  public String acceptFriendShip(String clientId, String friendshipId)
      throws DiamodoCheckedException {

    return friendshipService.acceptFriendShip(clientId, friendshipId);
  }

  @Override
  public String rejectFriendship(String clientId, String friendshipId)
      throws DiamodoCheckedException {

    return friendshipService.rejectFriendship(clientId, friendshipId);
  }

  @Override
  public String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException {
    return diamodoClientService.updateClientProfileImagePath(clientId, clientImageHolder);
  }

  @Override
  public List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException {
    return friendshipService.getMyFriends(clientId, pagingDTO);
  }

  @Override
  public List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException {
    return friendshipService.getMyFriendshipRequests( clientId,  pagingDTO) ;
  }



}
