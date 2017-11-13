package com.btx.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductFanout {
	private static final String EXCHAGE_NAME = "fanout_log";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
 
		Connection conn = connectionFactory.newConnection();
		//获取信道
		Channel channel = conn.createChannel();
		//交换器的类型：direct
		channel.exchangeDeclare(EXCHAGE_NAME, BuiltinExchangeType.FANOUT);
		
		
		String[] exchangKey = {"error" , "info" ,"warning"}; 
		
		for(int i=0; i<exchangKey.length; i++)
		{
			String message = "hello world" + exchangKey[i];
			//将消息发送的带有路由键的交换器上
			channel.basicPublish(EXCHAGE_NAME, exchangKey[i], null, message.getBytes());
			System.out.println("发送了："+message);
		}
		
		channel.close();
		conn.close();
	}
}
