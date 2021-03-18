package com.wellhope.dubbocenter.config;

import com.wellhope.contant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

/**
 * rabbitMQ生产者配置类
 * @author GaoJ
 * @create 2021-03-17 15:53
 */
public class RabbitConfig {
    /**
     * 创建Topic交换机
     * @return
     */
    @Bean
    public TopicExchange initProductExchange(){
        TopicExchange productExchange = new TopicExchange(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, true, false);
        return productExchange;

    }
}
