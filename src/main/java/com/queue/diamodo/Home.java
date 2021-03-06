package com.queue.diamodo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.gson.Gson;
import com.queue.diamodo.dataaccess.dto.AssignNewAdminsDTO;
import com.queue.diamodo.dataaccess.dto.BasicClientInfoDTO;
import com.queue.diamodo.dataaccess.dto.CreateConversationRequest;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.web.webservice.websocket.GetUnSeenMessagesRequest;
import com.queue.diamodo.web.webservice.websocket.SocketMessage;
import com.queue.diamodo.web.webservice.websocket.SocketMessageType;

public class Home {

  
  public static void main(String [] args)
  {
    
//    CreateConversationRequest conversationRequest = new CreateConversationRequest();
//    conversationRequest.setConversationName("mahmoud");
//    List<String> memberIds = new ArrayList();
//    memberIds.add("57d435f13405d21d9f3ef016");
//    memberIds.add("57d44d6c3405d240e4060a5a");
//    memberIds.add("57d44d7e3405d240e4060a5b");
//    memberIds.add("57d44d993405d240e4060a5c");
//    memberIds.add("57d44da33405d240e4060a5d");
//    conversationRequest.setMemberIds(memberIds);
//    conversationRequest.setConversationImage("base64Image");
//    String gson = new Gson().toJson(conversationRequest);
//    System.out.println(gson);
//    AssignNewAdminsDTO assignNewAdmins = new AssignNewAdminsDTO();
//    Set<String> memberIds = new HashSet<String>();
//    memberIds.add("57d435f13405d21d9f3ef016");
//    memberIds.add("57d44d6c3405d240e4060a5a");
//    
//    assignNewAdmins.setConversationId("57d44e5b3405d240e4060a5e");
//    assignNewAdmins.setAdminsIds(memberIds);
//    System.out.println(new Gson().toJson(assignNewAdmins));
    
//    PagingDTO pagingDTO = new PagingDTO();
//    
//    pagingDTO.setNumberOfResultNeeded(10);
//    
//    pagingDTO.setNumberOfResultsToSkip(0);
//    
//    System.out.println(new Gson().toJson(pagingDTO));
    
    
//    InviteMembersToConversationRequest inviteMembersToConversationRequest = new InviteMembersToConversationRequest();
//    
//    inviteMembersToConversationRequest.setConversationId("57d44e5b3405d240e4060a5e");
//    
//    Set<String> ids = new HashSet<String>();
//    ids.add("57d44d6c3405d240e4060a5a");
//    ids.add("57d44d993405d240e4060a5c");
//    ids.add("57d44da33405d240e4060a5d");
//    inviteMembersToConversationRequest.setMemberIds(ids);
//    
//    System.out.println(new Gson().toJson(inviteMembersToConversationRequest));
//    
//    PagingDTO pagingDTO = new PagingDTO();
//    pagingDTO.setNumberOfResultNeeded(10);
//    pagingDTO.setNumberOfResultsToSkip(0);
//    
    
//    SocketMessage socetMessage = new SocketMessage();
//    socetMessage.setSocketMessageType(SocketMessageType.GET_UNSEEN_MESSAGES);
//    
//    GetUnSeenMessagesRequest getUnSeenMessagesRequest = new GetUnSeenMessagesRequest();
//    
//    getUnSeenMessagesRequest.setClientId("clientId");
//    getUnSeenMessagesRequest.setConversationId("conversationUd");
//    getUnSeenMessagesRequest.setUserToken("userToken");
//    
//    socetMessage.setData(getUnSeenMessagesRequest);
//  System.out.println(new Gson().toJson(socetMessage));
    
    
//    Locale locale = new Locale("xx");
//    System.out.println(locale);

    // BasicClientInfoDTO basicClientInfoDTO = new BasicClientInfoDTO();
    // basicClientInfoDTO.setEmail("mahmoud.eltaieb@gmail.com");
    // basicClientInfoDTO.setFirstName("Mahmoud");
    // basicClientInfoDTO.setLastName("Eltaieb");
    // basicClientInfoDTO.setPassword("123456");
    // basicClientInfoDTO.setUserName("meltaieb");
    // System.out.println(new Gson().toJson(basicClientInfoDTO));
	  
	  
	  PagingDTO PagingDTO = new PagingDTO();
	  PagingDTO.setNumberOfResultNeeded(10);
	  PagingDTO.setNumberOfResultsToSkip(0);
	  System.out.println(new Gson().toJson(PagingDTO));
	  
//	  LoginDTO loginDTO = new LoginDTO();
//	  loginDTO.setLoginAttribute("meltaieb");
//	  loginDTO.setPassword("123456");
//	  System.out.println(new Gson().toJson(loginDTO));
	  
	  CreateConversationRequest createConversationRequest = new CreateConversationRequest();
	  createConversationRequest.setConversationName("MyTestConversation");

	  Set<String> ids = new HashSet<String>();
	  ids.add("57eed6e93405d2457388054c");
	  ids.add("57f11ad8ac04fb12ab8d5b21");
	  createConversationRequest.setMemberIds(ids);
	  
	  System.out.println(new Gson().toJson(createConversationRequest));

	  
	  

    
  }
}
