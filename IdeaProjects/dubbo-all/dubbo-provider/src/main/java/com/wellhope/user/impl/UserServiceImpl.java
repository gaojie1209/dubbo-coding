package com.wellhope.user.impl;

import com.wellhope.user.UserService;

/**
 * 把这个实现作为一个服务发布出来，方便其他工程调用
 *
 * @author GaoJ
 * @create 2021-02-28 22:23
 */
public class UserServiceImpl implements UserService {

    @Override
    public String hello(String name) {
        System.out.println(name);
        return "hello "+name;
    }
}
