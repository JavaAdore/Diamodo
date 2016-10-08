package com.queue.diamodo.dataaccess.daoimpl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;
import com.queue.diamodo.common.document.ChatMessage;
import com.queue.diamodo.common.document.Conversation;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.SeenByDTO;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.ChatMessageDAO;
import com.queue.diamodo.dataaccess.dto.GetMyConversationsResponseDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.web.webservice.websocket.OutboundChatSocketMessage;

@Repository
public class ChatMessageDAOImpl implements ChatMessageDAO {

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public void pushToConversation(String conversationId, ChatMessage chatMessage) {

		// Criteria criteria = new Criteria();
		// criteria.andOperator( .is(new ObjectId(sender))
		// ,Criteria.where("memberConversations.$.id").is(new
		// ObjectId(conversationId)) );
		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));

		List<BasicDBObject> list = mongoOperations.find(query, BasicDBObject.class, "conversation");

		System.out.println("well list size is " + list.size());

		Update update = new Update();
		update.addToSet("chatMessages", chatMessage);

		mongoOperations.updateFirst(query, update, Conversation.class);

	}

	@Override
	public boolean isConversationExist(String member, String conversationId) {

		Query query = new Query(Criteria.where("_id").is(new ObjectId(member)).and("memberConversations")
				.is(new DBRef("conversation", new ObjectId(conversationId))));

		DiamodoClient diamodoClient = mongoOperations.findOne(query, DiamodoClient.class);

		return diamodoClient != null;

	}

	@Override
	public void pushConversationToClient(String client, String conversationId) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(client)));

		Update update = new Update();
		update.addToSet("memberConversations", new DBRef("conversation", new ObjectId(conversationId)));

		mongoOperations.updateFirst(query, update, DiamodoClient.class);
	}

	@Override
	public List<OutboundChatSocketMessage> getUnseenMessages(String clientId, String conversationId) {
		Aggregation aggregation = newAggregation(DiamodoClient.class,
				match(where("_id").is(new ObjectId(conversationId))), unwind("chatMessages"),
				match(where("chatMessages.seenBy.diamodoClient")
						.nin((new DBRef("diamodoClient", new ObjectId(clientId))))),
				sort(Direction.DESC, "chatMessages.date"),

				new GroupOperation(Fields.fields("chatMessages"))

		);

		AggregationResults<OutboundChatSocketMessage> result = mongoOperations.aggregate(aggregation,
				Conversation.class, OutboundChatSocketMessage.class);

		return result.getMappedResults();
	}

	// should be update statement ...but just simple work around to make it
	// later
	@Override
	public void markConversationAsSeen(String clientId, String conversationId) {

		Conversation conversation = mongoOperations.findById(conversationId, Conversation.class);
		if (conversation != null) {
			final DiamodoClient diamodoClient = new DiamodoClient(clientId);
			conversation.getChatMessages().forEach(m -> {
				m.getSeenBy().add(new SeenByDTO(diamodoClient));
			});

			mongoOperations.save(conversation);

		}

	}

	@Override
	public Conversation getConversationById(String destinationId) {

		Query query = new Query(Criteria.where("_id").is(new ObjectId(destinationId)));

		Conversation conversation = mongoOperations.findOne(query, Conversation.class);
		return conversation;
	}

	@Override
	public Conversation saveConversation(Conversation conversation) {
		mongoOperations.save(conversation);
		return conversation;

	}

	@Override
	public void addNewAdministrators(String conversationId, Set<DiamodoClient> adminstrators) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));
		Update update = new Update();
		adminstrators.forEach(a -> {
			update.addToSet("conversationAdministrators", a);
			mongoOperations.updateMulti(query, update, Conversation.class);

		});

	}

	@Override
	public List<DiamodoClient> getConversationMembers(String clientId, String conversationId) {

		Conversation conversation = mongoOperations.findById(new ObjectId(conversationId), Conversation.class);
		if (conversation != null) {
			return new ArrayList<DiamodoClient>(conversation.getConversationMembers());
		}
		return new ArrayList<DiamodoClient>();
	}

	@Override
	public void leaveConversation(String clientId, String conversationId) {

		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));
		Update update = new Update();
		DiamodoClient clientToBeRemoved = new DiamodoClient(clientId);

		update.pull("conversationMembers", clientToBeRemoved);
		update.pull("conversationAdministrators", clientToBeRemoved);

		mongoOperations.updateMulti(query, update, Conversation.class);

	}

	@Override
	public List<GetMyConversationsResponseDTO> getMyConversations(String clientId, PagingDTO pagingDTO) {

		Criteria criteria = Criteria.where("isGroupChat").is(true).and("conversationMembers")
				.in(new DBRef("diamodoClient", new ObjectId(clientId)));

		Query query = new Query(criteria);
		query.skip(pagingDTO.getNumberOfResultsToSkip());
		query.limit(pagingDTO.getNumberOfResultNeeded());
		List<GetMyConversationsResponseDTO> result = mongoOperations.find(query, GetMyConversationsResponseDTO.class,
				"conversation");

		return result;
	}

	@Override
	public void inviteMemberToConversation(String conversationId, Set<String> memberIds) {

		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));
		Update update = new Update();
		memberIds.forEach(a -> {
			update.addToSet("conversationMembers", new DiamodoClient(a));
			mongoOperations.updateMulti(query, update, Conversation.class);

		});

	}

	@Override
	public String getConversationNameById(String conversationId) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));
		query.fields().include("conversationName");
		Conversation conversation = mongoOperations.findOne(query, Conversation.class);
		if (Utils.isNotEmpty(conversation)) {
			return conversation.getConversationName();
		}
		return null;
	}

	@Override
	public List<Conversation> getAllConversations() {
		return mongoOperations.findAll(Conversation.class);
	}

	@Override
	public Conversation getIsGroupConversation(String conversationId) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(conversationId)));
		query.fields().include("isGroupChat");
		Conversation conversation = mongoOperations.findOne(query, Conversation.class);
		return conversation;
	}

}
