package com.queue.diamodo.common.velocity;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;

@Service
public class DiamodoTemplateBean {

  @Autowired
  private VelocityEngine velocityEngine;

  @Autowired
  private DiamodoConfigurations diamodoConfigurations;

  public String prepareFriendRequestRecievedMessage(ClientInfo friendshipRequestSender) {
    Template template = velocityEngine.getTemplate("/templates/friendRequestRecieved.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", friendshipRequestSender.getFirstName());
    context.put("lastName", friendshipRequestSender.getLastName());

    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }


  public String preparePeerToPeerChatMessage(ClientInfo sender, String messageContent) {
    Template template =
        velocityEngine.getTemplate("/templates/peerToPeerChatMessageTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName());
    context.put("lastName", sender.getLastName());
    context.put("messageContent", getSubstredMessageContent(messageContent));
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }
  
  
  
  public String preparePeerToPeerBuzzMessage(ClientInfo sender) {
    Template template =
        velocityEngine.getTemplate("/templates/peerToPeerBuzzTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName());
    context.put("lastName", sender.getLastName());
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }



  public String prepareGroupChatMessage(ClientInfo sender, String conversationName,
      String messageContent) {
    Template template =
        velocityEngine.getTemplate("/templates/groupChatMessageTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName());
    context.put("lastName", sender.getLastName());
    context.put("conversationName", conversationName);
    context.put("messageContent", getSubstredMessageContent(messageContent));
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }
  
  
  
  public String preparePeerToPeerImageMessage(ClientInfo sender) {
    Template template =
        velocityEngine.getTemplate("/templates/peerToPeerImageTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName()); 
    context.put("lastName", sender.getLastName());
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }


  
  public String prepareGroupChatImageMessage(ClientInfo sender,String conversationName) {
    Template template =
        velocityEngine.getTemplate("/templates/groupImageTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName());
    context.put("lastName", sender.getLastName());
    context.put("conversationName", conversationName);

    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }
  
  
  
  public String prepareGroupBuzzMessage(ClientInfo sender,String conversationName) {
    Template template =
        velocityEngine.getTemplate("/templates/groupBuzzTemplate.vm");
    VelocityContext context = new VelocityContext();
    context.put("firstName", sender.getFirstName());
    context.put("lastName", sender.getLastName()); 
    context.put("conversationName", conversationName);   

    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }
  
  
  

  private String getSubstredMessageContent(String messageContent) {

    if (Utils.isNotEmpty(messageContent)) {

      if (messageContent.length() > diamodoConfigurations.PUSH_NOTIFICATION_DEFAULT_MESSAGE_CONTENT_LENGTH) {
        messageContent =
            messageContent
                .substring(diamodoConfigurations.PUSH_NOTIFICATION_DEFAULT_MESSAGE_CONTENT_LENGTH - 1)
                + "...";
      }
      return messageContent;

    }

    return "-";
  }
  
  
  
  
  public String prepareAddToConversationMessage(ClientInfo sender,String conversationName) {
	    Template template =
	        velocityEngine.getTemplate("/templates/addToConversationTemplate.vm");
	    VelocityContext context = new VelocityContext();
	    context.put("firstName", sender.getFirstName());
	    context.put("lastName", sender.getLastName());
	    context.put("conversationName", conversationName);

	    StringWriter writer = new StringWriter();
	    template.merge(context, writer);
	    return writer.toString();
	  }
	  


}
