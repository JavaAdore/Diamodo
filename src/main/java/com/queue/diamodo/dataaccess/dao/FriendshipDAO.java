package com.queue.diamodo.dataaccess.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;

@Repository
public interface FriendshipDAO extends MongoRepository<FriendSearchResult, String> {

  List<FriendSearchResult> findFriendsByEmail(String searcherId, String email,
      int numberOfResultToSkip, int numberOfResultNeeded);

  List<FriendSearchResult> findFriendsByUserName(String searcherId, String searchInput,
      int numberOfResultToSkip, int numberOfResultNeeded);

  Friendship getFriendshipBySenderOrReciever(String clientId, String friendId);

  Friendship addFriendshipToDiamodoClient(String clientId, Friendship friendShip);

  Friendship findFriendsById(String clientId, String friendshipId);

  void updateFriendshipStatus(String clientId, String friendshipId,
      int friendShipStatusAlreadyFriend);

  void deleteFriendShip(String clientId, String friendshipId);
   
  List<Friendship> getFrienshipIds(String clientId, int friendShipStatusAlreadyFriend,
      int numberOfResultNeeded, int numberOfResultsToSkip);

  List<FriendRepresentationDTO> getFriendRepresentation(String clientId,
      int friendShipStatusAlreadyFriend, int numberOfResultNeeded, int numberOfResultsToSkip);



}
