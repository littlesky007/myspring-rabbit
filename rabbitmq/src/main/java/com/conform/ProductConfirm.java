package com.conform;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductConfirm {
	private final static String EXCHANGE_NAME = "producer_confirm";
	private final static String ROUTE_KEY = "error";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		Connection connection = factory.newConnection();//连接

		Channel channel = connection.createChannel();//信道

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器

		//将信道设置为发送方确认
		channel.confirmSelect();

		for(int i=0;i<10;i++)
		{
			String message = "Hello world_"+(i+1);
			channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY, null, message.getBytes());
			if (channel.waitForConfirms()){
				System.out.println(ROUTE_KEY+":"+message);
			}
		}
		channel.close();
		connection.close();
	}
}
