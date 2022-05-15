package com.code;

import redis.clients.jedis.JedisCluster;

public class RedisClusterDemo {

    public static void main(String[] args) {
        JedisCluster jedis = RedisClusterUtil.getJedis();
        jedis.set("cluster", "hello world");
        System.out.println(jedis.get("cluster"));
    }
}
