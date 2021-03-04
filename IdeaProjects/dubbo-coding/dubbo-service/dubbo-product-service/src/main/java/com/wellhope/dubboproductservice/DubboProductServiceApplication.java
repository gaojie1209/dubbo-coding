package com.wellhope.dubboproductservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.wellhope.mapper")
public class DubboProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProductServiceApplication.class, args);
    }

}
