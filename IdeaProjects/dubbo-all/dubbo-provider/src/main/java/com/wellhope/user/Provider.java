package com.wellhope.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author GaoJ
 * @create 2021-02-28 22:40
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:dubbo-provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
