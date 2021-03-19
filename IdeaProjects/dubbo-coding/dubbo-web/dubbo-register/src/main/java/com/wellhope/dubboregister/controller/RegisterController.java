package com.wellhope.dubboregister.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wellhope.api.UserService;
import com.wellhope.entity.User;
import com.wellhope.pojo.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 注册系统
 * @author GaoJ
 * @create 2021-03-18 17:30
 */
@Controller
@RequestMapping("register")
public class RegisterController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(RegisterController.class);

    @Reference
    private UserService userService;

    @GetMapping("checkUserNameIsExists/{username}")
    @ResponseBody
    public ResultBean checkUserNameIsExists(@PathVariable("username") String username){
        return userService.checkUserNameIsExists(username);
    }

    @GetMapping("checkPhoneIsExists/{phone}")
    @ResponseBody
    public ResultBean checkPhoneIsExists(@PathVariable("phone") String phone){
        return userService.checkPhoneIsExists(phone);
    }

    @GetMapping("checkEmailIsExists/{email}")
    @ResponseBody
    public ResultBean checkEmailIsExists(@PathVariable("email") String email){
        return userService.checkEmailIsExists(email);
    }
    @PostMapping("generateCode/{identification}")
    @ResponseBody
    public ResultBean generateCode(@PathVariable("identification") String identification){
        return userService.generateCode(identification);
    }

    /**
     * 适合处理异步请求
     * @param user
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResultBean register(User user){
        return null;
    }

    /**
     * 适合处理同步请求，跳转到相关页面
     * @param user
     * @return
     */
    @PostMapping("register4PC")
    public String register4PC(User user){
        return null;
    }

    /**
     * 注册成功激活用户
     * @param token
     * @return
     */
    @GetMapping("activating")
    public String activating(String token){
        return null;
    }
}
