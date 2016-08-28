package com.queue.diamodo.business.service;

import java.util.List;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;


public interface DiamodoClientService {

  DiamodoClient signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException;

  DiamodoClient login(LoginDTO loginDTO) throws DiamodoCheckedException;

  void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException;

  String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException;




}
