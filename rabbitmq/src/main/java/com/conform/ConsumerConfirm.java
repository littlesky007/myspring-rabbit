package com.conform;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class ConsumerConfirm {
	private final static String EXCHANGE_NAME = "producer_confirm";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		Connection conn = connectionFactory.newConnection();

		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		//随机创建一个队列
		String queueName = channel.queueDeclare().getQueue();

		String[] exchangKey = {"error" , "info" ,"warning"}; 

		for(String key : exchangKey)
		{
			//将队列和交换器的路由键进行绑定
			/*channel.exchangeBind(queueName,EXCHANGE_NAME,key);*/
			channel.queueBind(queueName, EXCHANGE_NAME, key);
		}
		System.out.println("等待中...");

		//创建监听器
		Consumer consume = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				try {
					Thread.currentThread().sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String message = new String(body,"UTF-8");
				System.out.println(envelope.getRoutingKey()+":"+message);
			}
		};

		channel.basicConsume(queueName, true, consume);
	}
}
