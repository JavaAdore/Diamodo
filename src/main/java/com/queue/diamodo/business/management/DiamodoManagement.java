package com.queue.diamodo.business.management;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;


public interface DiamodoManagement {

  DiamodoClient signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException;

  DiamodoClient login(LoginDTO loginDTO) throws DiamodoCheckedException;

  void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException;


  List<FriendSearchResult> findFriends(String searcherId,
      FriendsSearchCriteriaDTO friendsSearchCriteriaDTO) throws DiamodoCheckedException;

  Friendship sendFriendRequest(String clientId, String friendId) throws DiamodoCheckedException;

  String acceptFriendShip(String clientId, String friendshipId) throws DiamodoCheckedException;

  String rejectFriendship(String clientId, String friendshipId) throws DiamodoCheckedException;

  String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;


}
