package com.queue.diamodo.dataaccess.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.queue.diamodo.common.document.DiamodoClient;

@Repository
public interface DiamodoClientDAO extends MongoRepository<DiamodoClient, String> {

  
  public void test();
  
}
