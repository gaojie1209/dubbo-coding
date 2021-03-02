package com.wellhope.dubbospringbootcomsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GaoJ
 * @create 2021-03-01 11:42
 */
@RestController
@RequestMapping("user")
public class UserController {
    //随机，按权重设置随机概率。在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
    //@Reference(loadbalance="random ")
    //轮询，按公约后的权重设置轮询比率。存在慢的提供者累积请求的问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
    //@Reference(loadbalance="roundrobin")
    //最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。
    //@Reference(loadbalance="leastactive")
    //一致性 Hash，相同参数的请求总是发到同一提供者。当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
    @Reference(loadbalance="consistenthash",version = "1.0.0",stub="com.wellhope.user.UserServiceStub")//version解决灰度发布问题,如果不需要区分版本version = "*"
   // @Reference(loadbalance="consistenthash ",timeout = 3000,cluster = "failfast")timeout超时链接时间；cluster = "failfast"快速失败
    private UserService userService;
    @RequestMapping("hello")
    public String hello(String name) {
        //调用远程服务
        return userService.hello(name);
    }
}
