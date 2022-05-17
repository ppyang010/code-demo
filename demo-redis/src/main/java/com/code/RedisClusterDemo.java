package com.code;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

public class RedisClusterDemo {

    public static void main(String[] args) {
        JedisCluster jedis = RedisClusterUtil.getJedis();
        jedis.set("cluster", "hello world");
        System.out.println(jedis.get("cluster"));
        int size = 100;
        List<String> keyList = new ArrayList<>(100);
        for (int i = 0; i < size; i++) {
            int x = RandomUtil.randomInt(0, 99999);
            String key = "test" + x;
            String val = "test" + x;
            keyList.add(key);
            jedis.set(key, val);
        }
        for (String key : keyList) {
            String res = jedis.get(key);
            String format = StrUtil.format("key={},val={}", key, res);
            System.out.println(format);
        }

    }
}
