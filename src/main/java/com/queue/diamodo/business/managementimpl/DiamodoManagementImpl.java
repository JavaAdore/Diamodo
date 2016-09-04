package com.queue.diamodo.business.managementimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.common.utils.*;
import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.business.service.ChatMessageService;
import com.queue.diamodo.business.service.DiamodoClientService;
import com.queue.diamodo.business.service.FriendshipService;
import com.queue.diamodo.common.document.ClientDevice;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
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
import com.queue.diamodo.dataaccess.dto.UserWithDeviceInfo;
import com.queue.diamodo.webservice.web.pushnotification.PushNotificationManager;
import com.queue.diamodo.webservice.websocket.InboundSocketChatMessage;
import com.queue.diamodo.webservice.websocket.LightConversation;
import com.queue.diamodo.webservice.websocket.OutboundChatSocketMessage;

@Service
public class DiamodoManagementImpl implements DiamodoManagement {

  Logger logger = Logger.getLogger(DiamodoManagementImpl.class);

  @Autowired
  private DiamodoClientService diamodoClientService;

  @Autowired
  private FriendshipService friendshipService;

  @Autowired
  private ChatMessageService chatMessageService;


  @Autowired
  private PushNotificationManager pushNotificationManager;

  @Override
  public UserInfoDTO signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException {

    return diamodoClientService.signUp(signUpDTO);
  }

  @Override
  public UserInfoDTO login(LoginDTO loginDTO) throws DiamodoCheckedException {
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
    logger.info("friendship sent successfully sender is :" + clientId + " reciever is : "
        + friendId);
    pushFriendshipRecievedNotification(friendId, clientId);
    return friendShip;
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
  public List<FriendRepresentationDTO> getMyFriends(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {
    return friendshipService.getMyFriends(clientId, pagingDTO);
  }

  @Override
  public List<FriendRepresentationDTO> getMyFriendshipRequests(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {
    return friendshipService.getMyFriendshipRequests(clientId, pagingDTO);
  }

  @Override
  public ClientInfo updateProfile(String clientId, UpdateProfileDTO updateProfile)
      throws DiamodoCheckedException {
    return diamodoClientService.updateProfilePicture(clientId, updateProfile);
  }

  @Override
  public void validateConversationMemebers(String clientId, String conversationId)
      throws DiamodoCheckedException {
    friendshipService.validateConversationMemebers(clientId, conversationId);
  }


  @Override
  public void validateClientExistance(String senderId) throws DiamodoCheckedException {
    // TODO Auto-generated method stub

  }

  @Override
  public ClientInfo getClientInfo(String clientId) {

    return friendshipService.getClientInfo(clientId);
  }



  @Override
  public void saveMessageToConversatation(String conversationId,
      OutboundChatSocketMessage outboundChatSocketMessage) {

    chatMessageService.saveMessageToConversation(conversationId, outboundChatSocketMessage);

  }

  @Override
  public void updateDeviceToken(String clientId, String deviceType, String deviceToken)
      throws DiamodoCheckedException {
    diamodoClientService.updateDeviceToken(clientId, deviceType, deviceToken);
  }

  @Override
  public boolean isValidClientToken(String clientId, String userToken) {
    return diamodoClientService.isValidClientToken(clientId, userToken);
  }

  @Override
  public List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId) {
    return chatMessageService.getUnseenMessages(clientId, conversationId);
  }

  @Override
  public void pushFriendshipRecievedNotification(String recieverId, String senderId) {
    logger.info("start pushing notification");
    logger.info("getting sender information dto");
    ClientInfo senderDTO = friendshipService.getClientInfo(senderId);
    logger.info("sender information dto is " + senderDTO);
    if (Utils.isNotEmpty(senderDTO)) {

      UserWithDeviceInfo userWithDeviceInfo =
          diamodoClientService.getClientWithDeviceInfo(recieverId);
      logger.info("getting reciver with device " + userWithDeviceInfo);

      if (Utils.isNotEmpty(userWithDeviceInfo)
          && userHasValidDevice(userWithDeviceInfo.getClientDevice())) {

        logger
            .info("reciever passed all validations and start pushing him / her friendship request");
        pushNotificationManager.pushFriendshipRecievedNotification(senderDTO, userWithDeviceInfo);

      }
    }
  }

  private boolean userHasValidDevice(ClientDevice clientDevice) {

    boolean result =
        clientDevice != null && clientDevice.isAcceptedDevice()
            && Utils.isNotEmpty(clientDevice.getDeviceToken());
    logger.info("user has %s" + (!result ? " not " : " ") + " valid device info ");
    return result;

  }

  @Override
  public List<String> getConversationMemberIds(String destinationId) {

    Conversation conversation = chatMessageService.getConversationById(destinationId);
    List<String> result = new ArrayList<String>();
    if (Utils.isNotEmpty(conversation)) {

      conversation.getConversationMembers().forEach(c -> {
        result.add(c.getId());
      });

    } else {
      Friendship friendship = friendshipService.getfriendshipById(destinationId);
      if (Utils.isNotEmpty(friendship)) {
        result.add(friendship.getPartOne().getId());
        result.add(friendship.getPartTwo().getId());
      }
    }

    return result;
  }

  @Override
  public Conversation ensureConversationExistance(String senderId, String destinationId) {

    Conversation conversation = chatMessageService.getConversationById(destinationId);
    if (conversation == null) {

      Friendship friendship = friendshipService.getfriendshipById(destinationId);
      if (Utils.isNotEmpty(friendship)) {
        Set<DiamodoClient> conversationMembers = new HashSet<DiamodoClient>();
        conversationMembers.add(friendship.getPartOne());
        conversationMembers.add(friendship.getPartTwo());
        conversation = new Conversation();
        conversation.setId(destinationId);
        conversation.setConversationInitiator(new DiamodoClient(senderId));
        conversation.setInitiationDate(Utils.getTimeInUTC());
        conversation.setConversationMembers(conversationMembers);
        conversation =  chatMessageService.saveConversation(conversation);
      }
    }

    Set<DiamodoClient> conversationMembers = conversation.getConversationMembers();
    final String conversationId = conversation.getId();
    conversationMembers.parallelStream().forEach(member -> {
      chatMessageService.ensureConversationExsistanceforMember(member.getId(), conversationId);

    });
    return conversation;
  }

  @Override
  public CreateConversationResponse createNewConversation(String clientId,
      CreateConversationRequest createConversationRequest) throws DiamodoCheckedException {
    return chatMessageService.createNewConversation(clientId, createConversationRequest);
  }

  @Override
  public void assignNewAdmin(String clientId, AssignNewAdminsDTO assignNewAdminsDTO)
      throws DiamodoCheckedException {

    chatMessageService.assignNewAdmin(clientId, assignNewAdminsDTO);
  }

  @Override
  public List<ClientInfo> getConversationMembers(String clientId, String conversationId)
      throws DiamodoCheckedException {
    return chatMessageService.getConversationMembers(clientId, conversationId);
  }

  @Override
  public void leaveConversation(String clientId, String conversationId)
      throws DiamodoCheckedException {
    chatMessageService.leaveConversation(clientId, conversationId);
  }

  @Override
  public List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {
    return chatMessageService.getMyConversations(clientId, pagingDTO);
  }

  @Override
  public void inviteMemberToConversation(String clientId,
      InviteMembersToConversationRequest inviteMembersToConversationRequest)
      throws DiamodoCheckedException {
    chatMessageService.inviteMemberToConversation(clientId, inviteMembersToConversationRequest);
  }

  @Override
  public void markConversationAsSeen(String clientId, String conversationId) {
    chatMessageService.markConversationAsSeen(clientId, conversationId);    
  }

  @Override
  public void sendPushNotificationForChatMessage(LightConversation conversation,
      InboundSocketChatMessage inboundSocketChatMessage, String recieverId) {
    logger.info("start pushing notification");
    logger.info("getting sender information dto");
    ClientInfo senderDTO = friendshipService.getClientInfo(inboundSocketChatMessage.getSenderId());
    logger.info("sender information dto is " + senderDTO);
    if (Utils.isNotEmpty(senderDTO)) {

      UserWithDeviceInfo userWithDeviceInfo =
          diamodoClientService.getClientWithDeviceInfo(recieverId);
      logger.info("getting reciver with device " + userWithDeviceInfo);

      if (Utils.isNotEmpty(userWithDeviceInfo)
          && userHasValidDevice(userWithDeviceInfo.getClientDevice())) {

        logger
            .info("reciever passed all validations and start pushing him / her friendship request");
        pushNotificationManager.pushOfflineMessageNotification(userWithDeviceInfo, senderDTO , conversation, inboundSocketChatMessage );

      }
    }
    
  }


}
