package com.wellhope.dubbosmsservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboSmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboSmsServiceApplication.class, args);
    }

}
