package com.wellhope.dubbospringbootprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wellhope.user.UserService;

/**
 * @author GaoJ
 * @create 2021-03-01 10:39
 */
@Service(version = "2.0.0")//version解决灰度发布问题
public class UserServiceImpl2 implements UserService {
    @Override
    public String hello(String name) {
        System.out.println(name);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "SpringBoot hello "+name;
    }
}
