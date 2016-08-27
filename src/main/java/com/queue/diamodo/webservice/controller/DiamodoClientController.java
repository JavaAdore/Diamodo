package com.queue.diamodo.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.queue.diamodo.business.management.DiamodoManagement;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;

@RestController()
@RequestMapping("/diamodoClientController")
public class DiamodoClientController {

  @Autowired
  private DiamodoManagement diamodoManagement;

  
  @Autowired
  DiamodoClientDAO diamodoClientDAO;

  @RequestMapping(value = "/getUserLocations", produces = {"application/json"})
  public void test() {
    try 
    {
      diamodoClientDAO.test();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
