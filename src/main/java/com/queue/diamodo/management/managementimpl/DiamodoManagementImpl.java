package com.queue.diamodo.management.managementimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.business.service.DiamodoClientService;

@Service
public class DiamodoManagementImpl implements DiamodoManagement {


  @Autowired
  private DiamodoClientService userLocationService;



}
