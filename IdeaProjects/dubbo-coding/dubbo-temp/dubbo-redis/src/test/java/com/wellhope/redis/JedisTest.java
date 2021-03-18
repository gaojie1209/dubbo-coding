package com.wellhope.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JedisTest
 * @author GaoJ
 * @create 2021-03-18 9:51
 */
public class JedisTest {

//    private static final Logger LOGGER  = LoggerFactory.getLogger(JedisTest.class);
    public  final String HOST="192.168.217.132";
    public  final int PORT=6379;
    public  final String PASSWORD="123456";
    /**
     *测试String类型
     */
    @Test
    public void stringTest(){
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.auth(PASSWORD);
        jedis.set("target","搞定redis");
        String target=jedis.get("target");
        System.out.println(target);
        for (int i = 0; i < 10; i++) {
            jedis.incr("count");
        }
        String count=jedis.get("count");
        System.out.println(count);
    }

    /**
     * 测试其他类型
     */
    @Test
    public void otherTest(){
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.auth(PASSWORD);
        //list
        jedis.lpush("list","a","b");
        jedis.rpush("list","a","b");
        jedis.lrange("list", 0, -1);
        //set
        Long sadd = jedis.sadd("set", "a", "a", "b");
        System.out.println(sadd);
        //zset
        Map<String, Double> map=new HashMap<>();
        map.put("java",100.0);
        map.put("php",200.0);
        jedis.zadd("hotbook",map);
    }

    /**
     * 批处理
     */
    @Test
    public void transactionTest(){
        Jedis jedis = new Jedis(HOST, PORT);
        jedis.auth(PASSWORD);
        //批处理
        Transaction transaction = jedis.multi();
        transaction.set("t1","v1");
        transaction.set("t2","v2");
        transaction.exec();
    }
}
