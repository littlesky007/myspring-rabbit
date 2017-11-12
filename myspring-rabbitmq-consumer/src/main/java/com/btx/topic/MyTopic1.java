package com.btx.topic;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

public class MyTopic1 implements MessageListener{

	@Override
	public void onMessage(Message message) {
		System.out.println("all:"+new String(message.getBody()));
	}

}
