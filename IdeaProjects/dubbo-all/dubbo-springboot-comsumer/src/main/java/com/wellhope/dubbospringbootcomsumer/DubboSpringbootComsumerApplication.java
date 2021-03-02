package com.wellhope.dubbospringbootcomsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource(locations = "classpath:dubbo-comsumer.xml")
@EnableDubbo
public class DubboSpringbootComsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DubboSpringbootComsumerApplication.class, args);
    }

}
