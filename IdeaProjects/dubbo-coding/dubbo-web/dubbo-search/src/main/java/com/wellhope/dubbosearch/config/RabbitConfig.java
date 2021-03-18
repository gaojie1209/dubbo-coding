package com.wellhope.dubbosearch.config;

import com.wellhope.contant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ消费者配置类
 * @author GaoJ
 * @create 2021-03-17 16:11
 */
@Configuration
public class RabbitConfig {
    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue initProductSearchQueue(){
        Queue queue = new Queue(
                MQConstant.QUEUE.CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE,true,false,false);
        return queue;
    }

    /**
     * 绑定交换机
     * @return
     */
    @Bean
    public TopicExchange initProductExchange(){
        TopicExchange productExchange = new TopicExchange(
                MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE,true,false);
        return productExchange;
    }

    /**
     * 队列绑定交换机
     * @param initProductSearchQueue
     * @param initProductExchange
     * @return
     */
    @Bean
    public Binding bindProductSearchQueueToProductExchange(
            Queue initProductSearchQueue,TopicExchange initProductExchange){
        return BindingBuilder.bind(initProductSearchQueue).to(initProductExchange).with("product.add");
    }
}
