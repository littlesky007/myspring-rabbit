package com.btx.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;


public class All_Error implements MessageListener{

	
	
	@Override
	public void onMessage(Message message) {
		System.out.println("all-error:"+new String(message.getBody()));
	}

}
