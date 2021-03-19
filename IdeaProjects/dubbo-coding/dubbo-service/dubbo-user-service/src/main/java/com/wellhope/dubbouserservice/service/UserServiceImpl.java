package com.wellhope.dubbouserservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wellhope.api.UserService;
import com.wellhope.base.BaseDao;
import com.wellhope.base.BaseServiceImpl;
import com.wellhope.entity.User;
import com.wellhope.mapper.UserMapper;
import com.wellhope.pojo.ResultBean;
import com.wellhope.utils.CodeUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author GaoJ
 * @create 2021-03-18 16:51
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
    @Override
    public ResultBean checkUserNameIsExists(String username) {
        return null;
    }

    @Override
    public ResultBean checkPhoneIsExists(String phone) {
        return null;
    }

    @Override
    public ResultBean checkEmailIsExists(String email) {
        return null;
    }

    @Override
    public ResultBean generateCode(String identification) {
        //1生成验证码
        String code = CodeUtils.generateCode(6);
        //2往redis保存一个凭证跟验证码的对应关系 key-value
        redisTemplate.opsForValue().set(identification,code,2, TimeUnit.MICROSECONDS);
        //3.发送消息，给手机发送验证码
        //3.1 调通阿里云提供的短信Demo
        //3.2 发送短信这个功能，整个体系很多系统都可能会用上，变成一个公共的服务
        //3.3 发送消息，异步处理发送短信
        Map<String,String> params=new HashMap<>();
        params.put("identification",identification);
        params.put("code",code);
        rabbitTemplate.convertAndSend("sms-exchange","sms.code",params);
        //
        return new ResultBean("200","OK");
    }


}
