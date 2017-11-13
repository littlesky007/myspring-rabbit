package com.conform;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.AMQP.BasicProperties;

public class ProductConfirmAy {
	private static final String EXCHANGE_NAME = "direct_log";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		Connection connection = factory.newConnection();//连接

		Channel channel = connection.createChannel();//信道

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器

		//将信道设置为发送方确认
		channel.confirmSelect();
		
		channel.addConfirmListener(new ConfirmListener() {
			
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				// TODO Auto-generated method stub
				System.out.println("handleNack:"+deliveryTag+",multiple:"+multiple);
			}
			
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				// TODO Auto-generated method stub
				System.out.println("handleAck:"+deliveryTag+",multiple:"+multiple);
			}
		});
		
		channel.addReturnListener(new ReturnListener() {
			
			@Override
			public void handleReturn(int arg0, String arg1, String arg2, String arg3, BasicProperties arg4, byte[] arg5)
					throws IOException {
				// TODO Auto-generated method stub
				String message = new String(arg5);
				
				System.out.println("replyCode:"+arg0+",replyText:"+arg1+",exchange:"+arg2+",routingKey:"+arg3+"message:"+message);
			}
		});
		
		String[] severities={"error","info","warning"};
        for(int i=0;i<3;i++){
            String severity = severities[i%3];
            // 发送的消息
            String message = "Hello World_"+(i+1)+("_"+System.currentTimeMillis());
            channel.basicPublish(EXCHANGE_NAME, severity, true,null, message.getBytes());
            //channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY,null,msg.getBytes());
            System.out.println("----------------------------------------------------");
            System.out.println(" Sent Message: [" + severity +"]:'"+ message + "'");
            Thread.sleep(2000);
        }
	}
}
