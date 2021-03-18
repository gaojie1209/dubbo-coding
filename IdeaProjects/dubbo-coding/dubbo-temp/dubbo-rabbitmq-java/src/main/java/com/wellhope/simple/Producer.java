package com.wellhope.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，负责发送消息队列
 * @author GaoJ
 * @create 2021-03-16 10:16
 */
public class Producer {

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
        //3声明队列
        //如果该队列不存在，则创建该队列，否则，不创建
        //第二个参数为消息是否持久化
        channel.queueDeclare("simple-queue",false,false,false,null);
        //4发送消息到队列上
        String message="消息队列是一个非常重要的中间件";
        //我们没有指定交换机
        //实际是使用默认交换机
        channel.basicPublish("","simple-queue",null,message.getBytes());

       System.out.println("发送消息成功！");
    }

}
