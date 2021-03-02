package com.wellhope.user;

/**
 * @author GaoJ
 * @create 2021-03-02 18:58
 */
public class UserServiceStub implements UserService{

    private final UserService userService;
    //构造函数传入真正的远程代理对象，服务对象会给我们注入具体的实现
    public UserServiceStub(UserService userService){
        this.userService = userService;
    }
    @Override
    public String hello(String name) {
        //此处编写统一的处理逻辑
        if(name == null || "".equals(name)){
            return "validate param";
        }
        return userService.hello(name);
    }
}
