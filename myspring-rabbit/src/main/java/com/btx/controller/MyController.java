package com.btx.controller;
/**
 * 
 * @author littlesky
 *
 */

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mycontrolelr")
public class MyController {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@RequestMapping("/send")
	public @ResponseBody String sendFanout(String message)
	{
		String mes = "fanout send ,"+message;
		rabbitTemplate.send("topic-exchange", "email.error", new Message(mes.getBytes(),new MessageProperties()));
		rabbitTemplate.send("topic-exchange", "email.warm", new Message(mes.getBytes(),new MessageProperties()));
		rabbitTemplate.send("topic-exchange", "email.info", new Message(mes.getBytes(),new MessageProperties()));

		
		rabbitTemplate.send("topic-exchange", "error.error", new Message(mes.getBytes(),new MessageProperties()));
		rabbitTemplate.send("topic-exchange", "error.warm", new Message(mes.getBytes(),new MessageProperties()));
		rabbitTemplate.send("topic-exchange", "error.info", new Message(mes.getBytes(),new MessageProperties()));
		return "success";
	}
}
