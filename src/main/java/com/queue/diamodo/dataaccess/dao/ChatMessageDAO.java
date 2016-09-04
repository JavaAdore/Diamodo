package com.queue.diamodo.dataaccess.dao;

import java.util.List;
import java.util.Set;

import com.queue.diamodo.common.document.ChatMessage;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.webservice.websocket.OutboundChatSocketMessage;

public interface ChatMessageDAO {

  void pushToConversation( String conversationI, ChatMessage chatMessage);

  boolean isConversationExist(String sender, String friendId);

  void pushConversationToClient(String client, String conversationId);

  List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId);

  void markConversationAsSeen(String clientId, String conversationId);

  Conversation getConversationById(String destinationId);

  Conversation saveConversation(Conversation conversation);

  void addNewAdministrators(String conversationId, Set<DiamodoClient> adminstrators);

  List<DiamodoClient> getConversationMembers(String clientId, String conversationId);

  void leaveConversation(String clientId, String conversationId);

  List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO);

  void inviteMemberToConversation(String conversationId,
      Set<String> memberIds);


}
