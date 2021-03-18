package com.wellhope.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，负责发送消息队列
 * @author GaoJ
 * @create 2021-03-16 10:16
 */
public class Producer {

    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1创建连接，连接MQ服务器
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/dubbo");
        connectionFactory.setUsername("dubbo");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        //2.基于这个链接对象，创建对应的通道
        Channel channel = connection.createChannel();
        //3声明交换机

       channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //4发送消息到队列上
        //我们没有指定交换机
        //实际是使用默认交换机
        for (int i = 0; i < 10; i++) {
            String message = "这是第"+i+"遍说我爱你";
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        }

       System.out.println("发送消息成功！");
    }

}
