package com.wellhope.dubboitem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 配置类
 * @author GaoJ
 * @create 2021-03-10 22:08
 */
@Configuration
public class CommonConfig {
    /**
     * 使用单例模式获取线程池
     * @return
     */
    @Bean
    public ThreadPoolExecutor initThreadPoolExecutor(){
        //获取当前服务器的cpu核数
        int processors = Runtime.getRuntime().availableProcessors();
        //自定义的线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                processors,processors*2,1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100));
        return pool;

    }
}
