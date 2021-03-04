package com.wellhope.dubbocenter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//exclude = DataSourceAutoConfiguration.class配置相关东西不用管
@EnableDubbo
public class DubboCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCenterApplication.class, args);
    }

}
