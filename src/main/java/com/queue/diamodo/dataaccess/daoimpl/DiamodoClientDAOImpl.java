package com.queue.diamodo.dataaccess.daoimpl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.queue.diamodo.common.document.ClientDevice;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.ProfileImage;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.UserWithDeviceInfo;



@Repository
public class DiamodoClientDAOImpl extends SimpleMongoRepository<DiamodoClient, String> implements
    DiamodoClientDAO {

  @Autowired
  MongoOperations mongoOperations;

  public DiamodoClientDAOImpl(MongoEntityInformation<DiamodoClient, String> metadata,
      MongoOperations mongoOperations) {
    super(metadata, mongoOperations);
  }

  @Autowired
  public DiamodoClientDAOImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
    this(factory.<DiamodoClient, String>getEntityInformation(DiamodoClient.class), mongoOperations);
  }


  @Override
  public List test() {



    Object obj =
        mongoOperations.findById(new ObjectId("57c4a96d3405d26cbe97100f"), DiamodoClient.class);
    Gson gson = new Gson();
    String json = gson.toJson(obj);
    System.out.println(json);

    return null;
  }

  @Override
  public DiamodoClient getBasicUserInformations(String userName, String email) {
    Criteria criteria = new Criteria();
    criteria.orOperator(Criteria.where("email").is(email), Criteria.where("userName").is(userName));

    Query query = new Query(criteria);
    query.fields().include("_id").include("firstName").include("lastName").include("email")
        .include("userName");

    return mongoOperations.findOne(query, DiamodoClient.class);


  }

  @Override
  public DiamodoClient getDiamodoClientByEmail(String email) {

    Query query = new Query(Criteria.where("email").is(email));
//    query.fields().include("_id").include("firstName").include("lastName").include("email")
//        .include("userName").include("password");

    return mongoOperations.findOne(query, DiamodoClient.class);
  }

  @Override
  public DiamodoClient getDiamodoClientByUserName(String userName) {
    Query query = new Query(Criteria.where("userName").is(userName));
//    query.fields().include("_id").include("firstName").include("lastName").include("email")
//        .include("userName").include("password");

    return mongoOperations.findOne(query, DiamodoClient.class);
  }

  @Override
  public ProfileImage getClientProfileImage(String clientId) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(clientId)));
    query.fields().include("currentProfileImage");
    DiamodoClient diamodoClient = mongoOperations.findOne(query, DiamodoClient.class);
    return diamodoClient.getCurrentProfileImage();
  }

  @Override
  public void addProfileImageToUserHistory(String clientId, ProfileImage currentProfileImage) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    update.addToSet("profilePicturesHistory", currentProfileImage);

    mongoOperations.updateFirst(query, update, DiamodoClient.class);

  }

  @Override
  public void setCurrentProfileImage(String clientId, ProfileImage profileImage) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    update.set("currentProfileImage", profileImage);

    mongoOperations.updateFirst(query, update, DiamodoClient.class);

  }

  @Override
  public DiamodoClient getBlockingInfoData(String clientId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(new ObjectId(clientId)));
    query.fields().include("accountsIBlock").include("accountsBlockMe");
    return mongoOperations.findOne(query, DiamodoClient.class);

  }

  @Override
  public ClientInfo getClientInfo(String id) {
    Query query = new Query();

    return mongoOperations.findById(id, ClientInfo.class, "diamodoClient");
  }

  @Override
  public void resetDB() {
    mongoOperations.dropCollection(DiamodoClient.class);

  }

  @Override
  public void updateClientInfo(String clientId, Map<String, Object> fieldsToUpdate) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    for (Entry<String, Object> entry : fieldsToUpdate.entrySet()) {

      update.set(entry.getKey(), entry.getValue());
    }

    mongoOperations.updateFirst(query, update, DiamodoClient.class);


  }

  @Override
  public void updateClientDevice(String clientId, ClientDevice clientDevice) {

    Query query = new Query(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    update.set("clientDevice", clientDevice);

    mongoOperations.updateFirst(query, update, DiamodoClient.class);

  }

  @Override
  public void updateUserToken(String clientId, String userToken) {
    Query query = new Query(Criteria.where("_id").is(new ObjectId(clientId)));

    Update update = new Update();
    update.set("userToken", userToken);
    mongoOperations.updateFirst(query, update, DiamodoClient.class);


  }

  @Override
  public DiamodoClient getUserByIdAndUserToken(String clientId, String userToken) {
    Query query =
        new Query(Criteria.where("_id").is(new ObjectId(clientId)).and("userToken").is(userToken));
    query.fields().include("userToken");

    DiamodoClient result = mongoOperations.findOne(query, DiamodoClient.class);

    return result;
  }

  @Override
  public UserWithDeviceInfo getClientWithDeviceInfo(String recieverId) {

    Query query = new Query(Criteria.where("_id").is(new ObjectId(recieverId)));


    UserWithDeviceInfo userWithDeviceInfo =
        mongoOperations.findOne(query, UserWithDeviceInfo.class, "diamodoClient");
    return userWithDeviceInfo;



  }


}
