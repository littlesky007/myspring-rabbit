package com.btx.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

public class Email_All implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("emaill-all:"+new String(message.getBody()));
	}

}
