package com.queue.diamodo.dataaccess.daoimpl;

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

import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;

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
  public void test() {

    // DiamodoClient diamodoClient = new DiamodoClient();
    // diamodoClient.setEmail("Mahmoud.eltaieb@gmail.com");
    // diamodoClient.setFirstName("Mahmoud");
    // diamodoClient.setLastName("Eltaieb");
    // diamodoClient.setPassword("123456");
    // diamodoClient.setUserName("meltaieb");

    // mongoOperations.save(diamodoClient);

    Update update = new Update();
    Query query = new Query(Criteria.where("_id").is(new ObjectId("57c0dabe3405d22d79b2a2a5")));
    update.addToSet("hobbies", new String[] {"swimming", "programming"});

    mongoOperations.updateFirst(query, update, DiamodoClient.class);
  }

}
