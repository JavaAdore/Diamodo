package com.queue.diamodo.business.serviceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.service.ChatMessageService;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.ChatMessage;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.SeenByDTO;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.ChatMessageDAO;
import com.queue.diamodo.dataaccess.dto.AssignNewAdminsDTO;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.CreateConversationRequest;
import com.queue.diamodo.dataaccess.dto.CreateConversationResponse;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.webservice.websocket.OutboundChatSocketMessage;

@Service
public class ChatMessageServiceImpl extends CommonService implements ChatMessageService {

  @Autowired
  private ChatMessageDAO chatMessageDAO;

  @Autowired
  private ModelMapper modelMapper;

  
  @Autowired
  private DiamodoConfigurations diamodoConfigurations;
    
  @Override
  public void saveMessageToConversation(String conversationId,
      OutboundChatSocketMessage outboundChatSocketMessage) {


    ChatMessage chatMessage =
        Utils.mapObjectToAnother(outboundChatSocketMessage, ChatMessage.class);
    chatMessage.getSeenBy().add(new SeenByDTO(new DiamodoClient(outboundChatSocketMessage.getSenderId())));
    chatMessageDAO.pushToConversation(conversationId, chatMessage);
 
  }
  


  @Override
  public List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId) {

    List<OutboundChatSocketMessage> outboundChatMessages =
        chatMessageDAO.getUnseenMessages(clientId, conversationId);

   markConversationAsSeen(clientId, conversationId);

    return outboundChatMessages;
  }


  @Override
  public void markConversationAsSeen(String clientId, String conversationId) {

    chatMessageDAO.markConversationAsSeen(clientId, conversationId);

  }



  @Override
  public Conversation getConversationById(String destinationId) {
    return chatMessageDAO.getConversationById(destinationId);
  }



  @Override
  public Conversation saveConversation(Conversation conversation) {
    conversation =chatMessageDAO.saveConversation(conversation);
    return conversation;
  }



  @Override
  public void ensureConversationExsistanceforMember(String memberId, String conversationId) {

    boolean isConversationExsist = chatMessageDAO.isConversationExist(memberId, conversationId);
    if (!isConversationExsist) {


      chatMessageDAO.pushConversationToClient(memberId, conversationId);

    }
  }



  @Override
  public CreateConversationResponse createNewConversation(String clientId,
      CreateConversationRequest createConversationRequest) throws DiamodoCheckedException {

    validateCreateConversationRequest(createConversationRequest);

    Conversation conversation = new Conversation();
    conversation.setInitiationDate(Utils.getTimeInUTC());

    conversation.setConversationName(createConversationRequest.getConversationName());
       
    conversation.getConversationMembers().add(new DiamodoClient(clientId));

    conversation.getConversationAdministrators().add(new DiamodoClient(clientId));

    createConversationRequest.getMemberIds().forEach(m -> {
      conversation.getConversationMembers().add(new DiamodoClient(m));
    });

    if (Utils.isNotEmpty(createConversationRequest.getConversationImage())) {

      String imageName =
          Utils.generateRandomToken() + "-" + Utils.generateRandomToken() + "-"
              + Utils.generateRandomToken();

      try{
      Utils.saveImageToServer(
          createConversationRequest.getConversationImage(),
          diamodoConfigurations.DEFAULT_UPLOAD_BASE_64_IMAGES_FILES_CONVERSATION_COVER_PHOTO_FILES_FOLDER_LOCATION
              + File.separator + imageName + ".png");
      conversation.setConversationImage(imageName);
      }catch(Exception ex)
      {
        // just ignore
        ex.printStackTrace();
       
      }
    }
    conversation.setGroupChat(true);

    chatMessageDAO.saveConversation(conversation);


    CreateConversationResponse createConversationResponse =
        modelMapper.map(conversation, CreateConversationResponse.class);
    return createConversationResponse;
  }



  private void validateCreateConversationRequest(CreateConversationRequest createConversationRequest)
      throws DiamodoCheckedException {
    // TODO Auto-generated method stub

  }



  @Override
  public void assignNewAdmin(String clientId, AssignNewAdminsDTO assignNewAdminsDTO)
      throws DiamodoCheckedException {

    validateAssignNewAdminsDTO(clientId, assignNewAdminsDTO);

    Set<DiamodoClient> adminstrators = new HashSet<DiamodoClient>();
    assignNewAdminsDTO.getAdminsIds().forEach(a -> {
      adminstrators.add(new DiamodoClient(a));
    });

    chatMessageDAO.addNewAdministrators(assignNewAdminsDTO.getConversationId(), adminstrators);


  }



  private void validateAssignNewAdminsDTO(String clientId, AssignNewAdminsDTO assignNewAdminsDTO)
      throws DiamodoCheckedException {
    // TODO Auto-generated method stub

  }



  @Override
  public List<ClientInfo> getConversationMembers(String clientId, String conversationId)
      throws DiamodoCheckedException {
    validateGetConversationMemebers(clientId, conversationId);
    List<DiamodoClient> diamodoClients =
        chatMessageDAO.getConversationMembers(clientId, conversationId);
    List<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();

    diamodoClients.parallelStream().forEach(diamodoClient -> {
      clientInfoList.add(modelMapper.map(diamodoClient, ClientInfo.class));
    });
    return clientInfoList;
  }



  private void validateGetConversationMemebers(String clientId, String conversationId)
      throws DiamodoCheckedException {
    // TODO Auto-generated method stub

  }



  @Override
  public void leaveConversation(String clientId, String conversationId)
      throws DiamodoCheckedException {
    validateLeaveConversation(clientId, conversationId);

    chatMessageDAO.leaveConversation(clientId, conversationId);
  }



  private void validateLeaveConversation(String clientId, String conversationId) {
    // TODO Auto-generated method stub

  }



  @Override
  public List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO)
      throws DiamodoCheckedException {
    return chatMessageDAO.getMyConversations(clientId, pagingDTO);
  }



  @Override
  public void inviteMemberToConversation(String clientId,
      InviteMembersToConversationRequest inviteMembersToConversationRequest)
      throws DiamodoCheckedException {

    valdiateInviteMembersToConversationRequest(inviteMembersToConversationRequest);
    chatMessageDAO.inviteMemberToConversation(
        inviteMembersToConversationRequest.getConversationId(),
        inviteMembersToConversationRequest.getMemberIds());
  }



  private void valdiateInviteMembersToConversationRequest(
      InviteMembersToConversationRequest inviteMembersToConversationRequest)
      throws DiamodoCheckedException {


  }



}
