package com.queue.diamodo.dataaccess.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.document.FriendshipHistory;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.PagingDTO;

@Repository
public interface FriendshipDAO extends MongoRepository<FriendSearchResult, String> {

  List<FriendSearchResult> findFriendsByEmail(String searcherId, String email,
      int numberOfResultToSkip, int numberOfResultNeeded);

  List<FriendSearchResult> findFriendsByUserName(String searcherId, String searchInput,
      int numberOfResultToSkip, int numberOfResultNeeded);

  Friendship getFriendshipBySenderOrReciever(String clientId, String friendId);

  Friendship addFriendshipToDiamodoClient(String clientId, Friendship friendShip);

  Friendship findFriendsById(String clientId, String friendshipId);

  void updatePartOneFriendshipStatus(String clientId, String friendshipId,
      int friendShipStatusAlreadyFriend);

  void deleteFriendShip(String clientId, String friendshipId);


  List<Friendship> getMyFriendshipRequests(String clientId, int numberOfResultNeeded,
      int numberOfResultsToSkip);

  Friendship addFriendShip(Friendship friendship);

  void updateFriendshipStatus(String friendshipId, int friendShipStatusAlreadyFriend);

  Friendship getfriendshipById(String friendshipId);

  List<Friendship> getMyFriends(String clientId,int numberOfResultNeeded,
      int numberOfResultsToSkip);

  void updateFriendshipSender(String clientId, String id);

  void updateFriendshipReciever(String friendId, String id);

  void updatePartTwoFriendshipStatus(String friendId, String id, int friendShipStatusRecieved);

  void addFriendshipHistory(String friendshipId, FriendshipHistory friendshipHistory);



}
