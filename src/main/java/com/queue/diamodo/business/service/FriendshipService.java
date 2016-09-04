package com.queue.diamodo.business.service;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;



public interface FriendshipService {

  List<FriendSearchResult> findFriends(String searcherId,
      FriendsSearchCriteriaDTO friendsSearchCriteriaDTO) throws DiamodoCheckedException;

  Friendship sendFriendRequest(String clientId, String friendId) throws DiamodoCheckedException;

  String acceptFriendShip(String clientId, String friendshipId) throws DiamodoCheckedException;

  String rejectFriendship(String clientId, String friendshipId) throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;

  void validateConversationMemebers(String clientId, String conversationId) throws DiamodoCheckedException;


  ClientInfo getClientInfo(String clientId);

  Friendship getfriendshipById(String destinationId);


}
