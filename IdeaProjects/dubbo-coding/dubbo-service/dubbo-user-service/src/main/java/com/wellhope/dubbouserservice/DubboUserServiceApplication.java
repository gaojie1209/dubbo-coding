package com.wellhope.dubbouserservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.wellhope.mapper")
public class DubboUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboUserServiceApplication.class, args);
    }

}
