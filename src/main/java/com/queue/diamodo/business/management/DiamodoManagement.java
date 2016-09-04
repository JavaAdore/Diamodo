package com.queue.diamodo.business.management;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.dataaccess.dto.AssignNewAdminsDTO;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.CreateConversationRequest;
import com.queue.diamodo.dataaccess.dto.CreateConversationResponse;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.FriendsSearchCriteriaDTO;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;
import com.queue.diamodo.dataaccess.dto.UpdateProfileDTO;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;
import com.queue.diamodo.webservice.websocket.InboundSocketChatMessage;
import com.queue.diamodo.webservice.websocket.LightConversation;
import com.queue.diamodo.webservice.websocket.OutboundChatSocketMessage;


public interface DiamodoManagement {

  UserInfoDTO signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException;

  UserInfoDTO login(LoginDTO loginDTO) throws DiamodoCheckedException;

  void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException;


  List<FriendSearchResult> findFriends(String searcherId,
      FriendsSearchCriteriaDTO friendsSearchCriteriaDTO) throws DiamodoCheckedException;

  Friendship sendFriendRequest(String clientId, String friendId) throws DiamodoCheckedException;

  String acceptFriendShip(String clientId, String friendshipId) throws DiamodoCheckedException;

  String rejectFriendship(String clientId, String friendshipId) throws DiamodoCheckedException;

  String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException;

  List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException;

  ClientInfo updateProfile(String clientId, UpdateProfileDTO updateProfile)
      throws DiamodoCheckedException;

  void validateConversationMemebers(String senderId, String conversationId)
      throws DiamodoCheckedException;

  void validateClientExistance(String clientId) throws DiamodoCheckedException;

  ClientInfo getClientInfo(String clientId);

  void saveMessageToConversatation(String memberId, OutboundChatSocketMessage outboundChatSocketMessage);

  void updateDeviceToken(String clientId, String deviceType, String deviceToken)
      throws DiamodoCheckedException;

  boolean isValidClientToken(String clientId, String userToken);


  List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId);


  public void pushFriendshipRecievedNotification(String recieverId, String senderId);

  List<String> getConversationMemberIds(String destinationId);

  Conversation ensureConversationExistance(String senderId, String destinationId);

  CreateConversationResponse createNewConversation(
      String clientId, CreateConversationRequest createConversationRequest) throws DiamodoCheckedException;

  void assignNewAdmin(String clientId, AssignNewAdminsDTO assignNewAdminsDTO) throws DiamodoCheckedException;

  List<ClientInfo> getConversationMembers(String clientId, String conversationId) throws DiamodoCheckedException;

  void leaveConversation(String clientId, String conversationId) throws DiamodoCheckedException;

  List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;

  void inviteMemberToConversation(String clientId,
      InviteMembersToConversationRequest inviteMembersToConversationRequest) throws DiamodoCheckedException;

  public void markConversationAsSeen(String clientId, String conversationId);

  void sendPushNotificationForChatMessage(LightConversation conversation,
      InboundSocketChatMessage inboundSocketChatMessage, String memberId);

}
