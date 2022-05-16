package com.code;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ccy
 * @description
 * @time 2022/5/5 4:30 PM
 */
public class RedisSentinelDemo {
    public static void main(String[] args) {
        //redis-cli -h 127.0.0.1 -p 6100
        //登录 redis-cli 命令行，输入monitor，即可进入到 redis 监控模式。
        //随后即可看到，当有请求时，redis 具体都做了什么
        Set<String> sentinels = new HashSet<>();
        sentinels.add("127.0.0.1:6300");
        sentinels.add("127.0.0.1:6301");
        sentinels.add("127.0.0.1:6302");

        String MASTER_NAME = "mymaster";
        JedisSentinelPool jspool = new JedisSentinelPool(MASTER_NAME, sentinels);
        Jedis jedis = jspool.getResource();
        System.out.println("port:" + jedis.getClient().getPort());

        for (int i = 0; i < 100; i++) {
            String key = "test" + i;
            String val = "test" + i;
            jedis.set(key, val);
        }
        jedis = jspool.getResource();
        System.out.println("port:" + jedis.getClient().getPort());
        for (int i = 0; i < 100; i++) {
            String key = "test" + i;
            String val = "test" + i;
            String res = jedis.get(val);
            System.out.println(res);
        }

        jspool.close();
    }
}
