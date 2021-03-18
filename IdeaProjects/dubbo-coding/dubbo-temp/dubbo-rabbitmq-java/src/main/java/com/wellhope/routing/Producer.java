package com.wellhope.routing;

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

    private static final String EXCHANGE_NAME = "direct_exchange";

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

       channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //4发送消息到队列上
        String message = "重大足球消息:国足今晚只要打平就能出线";
        channel.basicPublish(EXCHANGE_NAME,"football",null,message.getBytes());

        String message2 = "重要篮球消息：只要连续赢12场，我们就能出线";
        channel.basicPublish(EXCHANGE_NAME,"basketball",null,message2.getBytes());

       System.out.println("发送消息成功！");
    }

}
