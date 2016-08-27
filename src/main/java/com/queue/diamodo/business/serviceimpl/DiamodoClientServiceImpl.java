package com.queue.diamodo.business.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.service.DiamodoClientService;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;

@Service
public class DiamodoClientServiceImpl implements DiamodoClientService {


  @Autowired
  private DiamodoClientDAO diamodoClientDAO;



}
