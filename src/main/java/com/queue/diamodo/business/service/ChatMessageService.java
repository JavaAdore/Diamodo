package com.queue.diamodo.business.service;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.dataaccess.dto.AssignNewAdminsDTO;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.CreateConversationRequest;
import com.queue.diamodo.dataaccess.dto.CreateConversationResponse;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.web.webservice.websocket.OutboundChatSocketMessage;

public interface ChatMessageService {

  void saveMessageToConversation(String memberId, OutboundChatSocketMessage outboundChatSocketMessage);

  List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId);

  Conversation getConversationById(String destinationId);

  Conversation saveConversation(Conversation conversation);

  void ensureConversationExsistanceforMember(String memberId, String conversationId);

  CreateConversationResponse createNewConversation(
      String clientId, CreateConversationRequest createConversationRequest) throws DiamodoCheckedException;

  void assignNewAdmin(String clientId, AssignNewAdminsDTO assignNewAdminsDTO) throws DiamodoCheckedException;

  List<ClientInfo> getConversationMembers(String clientId, String conversationId) throws DiamodoCheckedException;

  void leaveConversation(String clientId, String conversationId) throws DiamodoCheckedException;

  List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO) throws DiamodoCheckedException;

  void inviteMemberToConversation(String clientId,
      InviteMembersToConversationRequest inviteMembersToConversationRequest) throws DiamodoCheckedException;

  void markConversationAsSeen(String clientId, String conversationId);



}
