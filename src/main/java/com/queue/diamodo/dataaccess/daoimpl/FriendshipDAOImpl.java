package com.queue.diamodo.dataaccess.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.Friendship;
import com.queue.diamodo.common.document.FriendshipHistory;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.FriendshipDAO;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.FriendSearchResult;
import com.queue.diamodo.dataaccess.dto.PagingDTO;

@Repository
public class FriendshipDAOImpl extends SimpleMongoRepository<FriendSearchResult, String> implements
    FriendshipDAO {

  Logger LOGGER = LogManager.getLogger(FriendshipDAOImpl.class);
  
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


    Criteria criteria =
        new Criteria().orOperator(
            Criteria.where("partOne").is(new DBRef("diamodoClient", clientId)).and("partTwo")
                .is(new DBRef("diamodoClient", friendId)),
            Criteria.where("partOne").is(new DBRef("diamodoClient", friendId)).and("partTwo")
                .is(new DBRef("diamodoClient", clientId)));

    Query query = new Query(criteria);

    Friendship friendship = mongoOperations.findOne(query, Friendship.class);

    return friendship;


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

    Criteria criteria =
        Criteria
            .where("_id")
            .is(new ObjectId(friendshipId))
            .andOperator(
                new Criteria().orOperator(
                    Criteria.where("partOne").is(new DBRef("diamodoClient", clientId)), Criteria
                        .where("partTwo").is(new DBRef("diamodoClient", clientId))));
    Query query = new Query(criteria);

    Friendship friendship = mongoOperations.findOne(query, Friendship.class);

    System.out.println("frindship is " + friendshipId);

    return friendship;


  }

  @Override
  public void updatePartOneFriendshipStatus(String clientId, String friendshipId, int friendshipStatus) {

    Criteria criteria = Criteria.where("_id").is(new ObjectId(friendshipId));
   

    Query query = new Query(criteria);


    Update update = new Update();
    update.set("partOneFrienshipStatus", friendshipStatus);
    mongoOperations.updateFirst(query , update , Friendship.class);

  }

  @Override
  public void deleteFriendShip(String clientId, String friendshipId) {
    // TODO Auto-generated method stub

  }



  @Override
  public List<Friendship> getMyFriendshipRequests(String clientId, int numberOfResultNeeded,
      int numberOfResultsToSkip) {



    Query query =
        new Query().addCriteria(Criteria.where("partTwo.$id").is(new ObjectId(clientId))
            .and("partTwoFrienshipStatus").is(Friendship.FRIEND_SHIP_STATUS_RECIEVED));;
    query.skip(numberOfResultsToSkip);
    query.limit(numberOfResultNeeded);
    List<Friendship> result = mongoOperations.find(query, Friendship.class, "friendship");

    return result;

    // Aggregation aggregation =
    // newAggregation(
    // DiamodoClient.class, //
    // match(where("_id").is(clientId)),
    // unwind("friendships"),//
    // match(where("friendships.friendShipStatus").is(status)),
    // new GroupOperation(Fields.fields("friendships")), new SkipOperation(
    // numberOfResultsToSkip), new LimitOperation(numberOfResultNeeded));
    // AggregationResults<FriendRepresentationDTO> ggregationResults =
    // mongoOperations.aggregate(aggregation, DiamodoClient.class, FriendRepresentationDTO.class);
    // System.out.println("result of get my frienships with status " + status + " for user "
    // + clientId + " is " + ggregationResults.getMappedResults());
    // return ggregationResults.getMappedResults();



    // DiamodoClient diamodoClient = mongoOperations.getConverter().read(DiamodoClient.class,
    // result.getUniqueMappedResult().);
    // List<Friendship> friendship = diamodoClient.getFriendships();

  }

  @Override
  public Friendship addFriendShip(Friendship friendship) {
    mongoOperations.save(friendship);
    return friendship;
  }

  @Override
  public void updateFriendshipStatus(String friendshipId, int friendshipSatatus) {
    Criteria criteria = Criteria.where("_id").is(new ObjectId(friendshipId));
    Query query = new Query(criteria);
    Update update = new Update();
    update.set("partOneFrienshipStatus", friendshipSatatus);
    update.set("partTwoFrienshipStatus", friendshipSatatus);

    mongoOperations.updateFirst(query, update, Friendship.class);


  }

  @Override
  public Friendship getfriendshipById(String friendshipId) {
    Friendship friendship =  mongoOperations.findById(friendshipId, Friendship.class);
    LOGGER.debug(String.format("friend ship with id %s does%s exsist", friendshipId , friendship==null?" not ":""));
    return friendship;

  }

  @Override
  public List<Friendship> getMyFriends(String clientId, int numberOfResultNeeded,
      int numberOfResultsToSkip) {
    Criteria criteria =
        new Criteria().andOperator(new Criteria().orOperator(
            Criteria.where("partOne.$id").is(new ObjectId(clientId)),
            Criteria.where("partTwo.$id").is(new ObjectId(clientId))),
            Criteria.where("partOneFrienshipStatus").is(
                Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND),
            Criteria.where("partTwoFrienshipStatus").is(
                Friendship.FRIEND_SHIP_STATUS_ALREADY_FRIEND));


    Query query = new Query(criteria);
    query.skip(numberOfResultsToSkip);
    query.limit(numberOfResultNeeded);
    List<Friendship> result = mongoOperations.find(query, Friendship.class, "friendship");
    return result;
  }

  @Override
  public void updateFriendshipSender(String clientId, String friendshipId) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(friendshipId)));
    
    Update update = new Update();
    update.set("partOne", new DBRef("diamodoClient", new ObjectId(clientId)));
    update.set("friendShipRequestSender",  new DBRef("diamodoClient", new ObjectId(clientId)));      
    mongoOperations.updateFirst(query, update, Friendship.class);
         
  }    

  @Override
  public void updateFriendshipReciever(String friendId, String friendshipId) {
 Query query = new Query(Criteria.where("_id").is(new ObjectId(friendshipId)));
    
    Update update = new Update();
    update.set("partTwo", new DBRef("diamodoClient", new ObjectId(friendId)));
    
    mongoOperations.updateFirst(query, update, Friendship.class);
    
  }

  @Override
  public void updatePartTwoFriendshipStatus(String friendId, String friendshipId, int friendshipStatus) {
   
    Criteria criteria = Criteria.where("_id").is(new ObjectId(friendshipId));
    

    Query query = new Query(criteria);


    Update update = new Update();
    update.set("partTwoFrienshipStatus", friendshipStatus);
    
    mongoOperations.updateFirst(query , update , Friendship.class);
    
  }

  @Override
  public void addFriendshipHistory(String friendshipId, FriendshipHistory friendshipHistory) {
    
    Criteria criteria = Criteria.where("_id").is(new ObjectId(friendshipId));
    
    Query query = new Query(criteria);

    Update update = new Update();
    update.push("friendshipHistory", friendshipHistory);
    
    mongoOperations.updateFirst(query , update , Friendship.class);

  }



}
