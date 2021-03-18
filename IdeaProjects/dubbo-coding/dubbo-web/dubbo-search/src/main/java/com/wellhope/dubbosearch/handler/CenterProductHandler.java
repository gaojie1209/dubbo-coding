package com.wellhope.dubbosearch.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.api.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ消费者配置类
 * @author GaoJ
 * @create 2021-03-16 15:34
 */
@Component
@RabbitListener(queues = "center-product-exchange-search-queue")
public class CenterProductHandler {
    // private static final Logger LOGGER  = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
   private static final Logger LOGGER  = LoggerFactory.getLogger(CenterProductHandler.class);
    //1声明队列 center-product-exchange-search-queue
    //2绑定交换机
    //借助管理平台来实现

    @Reference
    private SearchService searchService;
    @RabbitHandler
    public void process(Long newId){
        LOGGER.info("处理id为：{}的消息",newId);
        searchService.synById(newId);
    }

}
