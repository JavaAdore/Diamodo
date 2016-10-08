package com.queue.diamodo.business.service;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;
import com.queue.diamodo.dataaccess.dto.UpdateProfileDTO;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;
import com.queue.diamodo.dataaccess.dto.UserWithDeviceInfo;


public interface DiamodoClientService {

  UserInfoDTO signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException;

  UserInfoDTO login(LoginDTO loginDTO) throws DiamodoCheckedException;

  void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException;

  String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException;

  ClientInfo updateProfilePicture(String clientId, UpdateProfileDTO updateProfile)  throws DiamodoCheckedException;

  void updateDeviceToken(String clientId, String deviceType, String deviceToken) throws DiamodoCheckedException;

  boolean isValidClientToken(String clientId, String userToken);

  UserWithDeviceInfo getClientWithDeviceInfo(String recieverId);

ClientInfo getClientById(String clientId);




}
