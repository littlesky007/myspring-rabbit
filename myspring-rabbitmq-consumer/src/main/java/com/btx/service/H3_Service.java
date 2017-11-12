package com.btx.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class H3_Service /*implements MessageListener*/{

	
	public void onMessage(Message message) {
		String mes = new String(message.getBody());
		System.out.println("fanout get message:"+mes);
	}

}
