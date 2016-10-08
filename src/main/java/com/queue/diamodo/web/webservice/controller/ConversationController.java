package com.queue.diamodo.web.webservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.business.service.ChatMessageService;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.dataaccess.dto.AssignNewAdminsDTO;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.CreateConversationRequest;
import com.queue.diamodo.dataaccess.dto.CreateConversationResponse;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.InviteMembersToConversationRequest;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.web.webservice.common.DiamodoResponse;
import com.queue.diamodo.web.webservice.websocket.DiamodoEndPoint;
import com.queue.diamodo.web.webservice.websocket.OutboundChatSocketMessage;

@RestController()
@RequestMapping("/conversationController")
public class ConversationController {

	@Autowired
	private DiamodoManagement diamodoManagement;

	@Autowired
	private DiamodoConfigurations diamodoConfigurations;
	// just temperary and to be removed
	@Autowired
	private ChatMessageService chatMessageService;

	@RequestMapping(value = "/getUnseenMessages", produces = { "application/json" })
	public Object getUnseenConversationMessages(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestParam(name = "conversationId", required = true) String conversationId, Locale locale) {

		List<OutboundChatSocketMessage> result = diamodoManagement.getUnseenMessages(clientId, conversationId);
		return result;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/createNewConversation", produces = { "application/json" })
	public Object createNewConversation(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestBody CreateConversationRequest createConversationRequest, Locale locale) {

		try {
			CreateConversationResponse createConversationResponse = diamodoManagement.createNewConversation(clientId,
					createConversationRequest);
			return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(createConversationResponse));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/assignNewAdmins", produces = { "application/json" })
	public Object assignNewAdmin(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestBody AssignNewAdminsDTO assignNewAdminsDTO, Locale locale) {

		try {
			diamodoManagement.assignNewAdmin(clientId, assignNewAdminsDTO);
			return ResponseEntity.ok(DiamodoResponse.prepareDefaultSuccessResponse(locale));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}
	}

	@RequestMapping(value = "/getConversationMembers", produces = { "application/json" })
	public Object getConversationMembers(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestParam(name = "conversationId") String conversationId, Locale locale) {

		try {
			List<ClientInfo> result = diamodoManagement.getConversationMembers(clientId, conversationId);
			return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(result));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}

	}

	@RequestMapping(value = "/leaveConversation", produces = { "application/json" })
	public Object leaveConversation(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestParam(name = "conversationId") String conversationId, Locale locale) {
		try {
			diamodoManagement.leaveConversation(clientId, conversationId);
			DiamodoEndPoint.leaveConversation(clientId, conversationId);
			return ResponseEntity.ok(DiamodoResponse.prepareDefaultSuccessResponse(locale));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}

	}

	@RequestMapping(value = "/getMyConversations", produces = { "application/json" })
	public Object getMyConversations(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken, @RequestBody PagingDTO pagingDTO,
			Locale locale) {
		try {

			List<GetMyConversationsResponseDTO> result = diamodoManagement.getMyConversations(clientId, pagingDTO);

			return ResponseEntity.ok(DiamodoResponse.prepareSuccessResponse(result));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/inviteMembersToConversation", produces = {
			"application/json" })
	public Object inviteNewMembers(@RequestHeader(name = "clientId", required = true) String clientId,
			@RequestHeader(name = "userToken", required = true) String userToken,
			@RequestBody InviteMembersToConversationRequest inviteMembersToConversationRequest, Locale locale) {

		try {
			diamodoManagement.inviteMemberToConversation(clientId, inviteMembersToConversationRequest);
			return ResponseEntity.ok(DiamodoResponse.prepareDefaultSuccessResponse(locale));

		} catch (DiamodoCheckedException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareFailureResponse(ex, locale));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(DiamodoResponse.prepareBackendErrorResponse(locale));
		}
	}
	
	
	
	@RequestMapping(value="/updateChatMessages")
	public void updateChatMessages()
	{
		chatMessageService.updateChatMessages();
	}

}
