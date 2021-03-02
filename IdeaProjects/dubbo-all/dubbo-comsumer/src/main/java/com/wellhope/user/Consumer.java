package com.wellhope.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author GaoJ
 * @create 2021-03-01 10:11
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-comsumer.xml"});
        context.start();
        //调用远程服务,感觉跟本地调用的方式一样
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.hello("dubbo"));

        System.in.read(); // 按任意键退出
    }
}
