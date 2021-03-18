package com.wellhope.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * SpringDataRedisTest
 * @author GaoJ
 * @create 2021-03-18 9:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:redis.xml")
public class SpringDataRedisTest {
    //IOC
    //controller service mapper
    //spring整合第三方开发API
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void stringTest(){
        //序列化
        //String String序列化方式
        redisTemplate.opsForValue().set("smallTraget","1个亿");
        String smallTraget = redisTemplate.opsForValue().get("smallTraget");
        System.out.println(smallTraget);
    }

    @Test
    public void hashTest(){
        redisTemplate.opsForHash().put("book","name","好好学习");
        Object o = redisTemplate.opsForHash().get("book", "name");
        System.out.println(o);
    }
    @Test
    public void numberTest(){
        //修改序列化方式
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //value默认是JDK的序列化方式
        redisTemplate.opsForValue().set("money","1000");
        //不能支持进行数学运算
        redisTemplate.opsForValue().increment("money",1000);
        String money = redisTemplate.opsForValue().get("money");
        System.out.println(money);
    }
    @Test
    public void otherTest(){
        //方法名没有linux跟指令的名称一对一
        redisTemplate.opsForValue().increment("money",-1000);//减法
    }

    /**
     * 流水线（批处理）
     */
    @Test
    public void runTest(){
        long start = System.currentTimeMillis();
        //
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    operations.opsForValue().set("k"+i,"v"+i);
                }
                return null;
            }
        });
        //
        long end = System.currentTimeMillis();
        System.out.println(end-start);//19431  1019
    }

    /**
     * 超时指令
     */
    @Test
    public void ttlTest(){
        redisTemplate.opsForValue().set("18841757021","666");
        //设置有效期
        redisTemplate.expire("18841757021",60, TimeUnit.SECONDS);
        //获取有效期
        Long expire = redisTemplate.getExpire("18841757021");
        System.out.println(expire);

    }
}
