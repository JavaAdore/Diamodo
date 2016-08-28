package com.queue.diamodo.dataaccess.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.ProfileImage;
import com.queue.diamodo.dataaccess.dto.FriendInfo;

@Repository
public interface DiamodoClientDAO extends MongoRepository<DiamodoClient, String> {


  public List test();

  public DiamodoClient getBasicUserInformations(String userName, String email);

  public DiamodoClient getDiamodoClientByEmail(String authenticationAttribute);

  public DiamodoClient getDiamodoClientByUserName(String authenticationAttribute);

  public ProfileImage getClientProfileImage(String clientId);

  public void addProfileImageToUserHistory(String clientId, ProfileImage currentProfileImage);

  public void setCurrentProfileImage(String clientId, ProfileImage profileImage);

  public DiamodoClient getBlockingInfoData(String clientId);

  public FriendInfo getFriendInfo(String friendId);

  public void resetDB();

}
