package com.queue.diamodo.dataaccess.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.DBRef;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.FriendshipDAO;
import com.queue.diamodo.dataaccess.dto.FriendInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;

@Repository
public class FriendshipDAOImpl extends SimpleMongoRepository<FriendSearchResult, String> implements
    FriendshipDAO {

  @Autowired
  MongoOperations mongoOperations;

  @Autowired
  private ModelMapper modelMapper;

  public FriendshipDAOImpl(MongoEntityInformation<FriendSearchResult, String> metadata,
      MongoOperations mongoOperations) {
    super(metadata, mongoOperations);
  }

  @Autowired
  public FriendshipDAOImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
    this(factory.<FriendSearchResult, String>getEntityInformation(FriendSearchResult.class),
        mongoOperations);
  }

  @Override
  public List<FriendSearchResult> findFriendsByEmail(String searcherId, String email,
      int numberOfResultToSkip, int numberOfResultNeeded) {



    Query query =
        new Query(new Criteria().andOperator(Criteria.where("_id").ne(new ObjectId(searcherId)),
            Criteria.where("email").regex(email, "i")));

    query.fields().include("id").include("currentProfileImage").include("firstName")
        .include("lastName").include("email").include("userName");

    query.skip(numberOfResultToSkip);

    query.limit(numberOfResultNeeded);

    return mongoOperations.find(query, FriendSearchResult.class, "diamodoClient");


  }

  @Override
  public List<FriendSearchResult> findFriendsByUserName(String searcherId, String userName,
      int numberOfResultToSkip, int numberOfResultNeeded) {

    Query query =
        new Query(new Criteria().andOperator(Criteria.where("_id").ne(new ObjectId(searcherId)),
            Criteria.where("userName").regex(userName, "i")));

    query.fields().include("id").include("currentProfileImage").include("firstName")
        .include("lastName").include("email").include("userName");


    query.skip(numberOfResultToSkip);

    query.limit(numberOfResultNeeded);

    return mongoOperations.find(query, FriendSearchResult.class, "diamodoClient");
  }

  @Override
  public Friendship getFriendshipBySenderOrReciever(String clientId, String friendId) {

    Query query =
        new Query(new Criteria().andOperator(
            Criteria.where("_id").is(new ObjectId(clientId)),
            Criteria.where("friendships.friend").is(
                new DBRef("diamodoClient", new ObjectId(friendId)))));
    DiamodoClient diamodoClient =
        mongoOperations.findOne(query, DiamodoClient.class, "diamodoClient");
    if (Utils.isEmpty(diamodoClient)) {
      return null;
    }
    return Utils.getFirstResult(diamodoClient.getFriendships());
  }

  @Override
  public Friendship addFriendshipToDiamodoClient(String clientId, Friendship friendShip) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    update.addToSet("friendships", friendShip);

    mongoOperations.updateFirst(query, update, DiamodoClient.class);

    return friendShip;
  }

  @Override
  public Friendship findFriendsById(String clientId, String friendshipId) {

    Query query =
        new Query(new Criteria().andOperator(Criteria.where("_id").is(new ObjectId(clientId)),
            Criteria.where("friendships.id").is(new ObjectId(friendshipId))));

    query.fields().include("friendships");

    DiamodoClient diamodoClient =
        mongoOperations.findOne(query, DiamodoClient.class, "diamodoClient");
    if (Utils.isEmpty(diamodoClient)) {
      return null;
    }
    return Utils.getFirstResult(diamodoClient.getFriendships());
  }

  @Override
  public void updateFriendshipStatus(String clientId, String friendshipId, int friendshipStatus) {

    mongoOperations.updateFirst(
        new Query(new Criteria().andOperator(Criteria.where("_id").is(new ObjectId(clientId)),
            Criteria.where("friendships.id").is(new ObjectId(friendshipId)))), new Update().set(
            "friendships.$.friendShipStatus", friendshipStatus), DiamodoClient.class);

  }

  @Override
  public void deleteFriendShip(String clientId, String friendshipId) {
    // TODO Auto-generated method stub

  }

  

  @Override
  public List<Friendship> getFrienshipIds(String clientId, int friendShipStatusAlreadyFriend,
      int numberOfResultNeeded, int numberOfResultsToSkip) {
    Query query =
        new Query(new Criteria().andOperator(Criteria.where("_id").is(new ObjectId(clientId))));
    query.fields().include("friendships.id").include("friendships.friendId");

    DiamodoClient diamodoClient = mongoOperations.findOne(query, DiamodoClient.class);
    if (diamodoClient != null && Utils.isNotEmpty(diamodoClient.getFriendships())) {
      return diamodoClient.getFriendships();

    }
    return new ArrayList<Friendship>();
  }

  @Override
  public List<FriendRepresentationDTO> getFriendRepresentation(String clientId, int status,
      int numberOfResultNeeded, int numberOfResultsToSkip) {


    Query query =
        new Query(new Criteria().andOperator(Criteria.where("_id").is(new ObjectId(clientId)),
            Criteria.where("friendships.friendShipStatus").is(status)));
    query.fields().include("friendships._id").include("friendships.friend");
    query.limit(numberOfResultNeeded);     
    query.skip(numberOfResultsToSkip);
    DiamodoClient diamodoClient = mongoOperations.findOne(query, DiamodoClient.class);
    List<FriendRepresentationDTO> result = new ArrayList<FriendRepresentationDTO>();
    if (Utils.isNotEmpty(diamodoClient)) {
      List<Friendship> friendshipList = diamodoClient.getFriendships();
      if (friendshipList != null) {

        friendshipList.parallelStream().forEach(
            friendship -> {
              result.add(new FriendRepresentationDTO(friendship.getId(), modelMapper.map(
                  friendship.getFriend(), FriendInfo.class)));
            });
      }
    }
    return result;
  }



}
