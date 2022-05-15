package com.code;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 参考:
 * https://cloud.tencent.com/developer/article/1683397
 */
public class RedisClusterUtil {

    private static JedisCluster jedis = null;

    //可用连接实例的最大数目，默认为8；
    //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
    private static Integer MAX_TOTAL = 1024;
    //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
    private static Integer MAX_IDLE = 200;
    //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static Integer MAX_WAIT_MILLIS = 10000;
    //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
    //如果为true，则得到的jedis实例均是可用的
    private static Boolean TEST_ON_BORROW = true;
    //在空闲时检查有效性, 默认false
    private static Boolean TEST_WHILE_IDLE = true;
    //是否进行有效性检查
    private static Boolean TEST_ON_RETURN = true;

    //访问密码
    private static String AUTH = "1234@abcd";

    /**
     * 静态块，初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
        /*注意：
            在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
            这是因为高版本中官方废弃了此方法，用以下两个属性替换。
            maxActive  ==>  maxTotal
            maxWait==>  maxWaitMillis
         */
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestWhileIdle(TEST_WHILE_IDLE);
            config.setTestOnReturn(TEST_ON_RETURN);

            Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
            jedisClusterNode.add(new HostAndPort("192.168.0.31", 6380));
            jedisClusterNode.add(new HostAndPort("192.168.0.32", 6380));
            jedisClusterNode.add(new HostAndPort("192.168.0.33", 6380));
            jedisClusterNode.add(new HostAndPort("192.168.0.34", 6380));
            jedisClusterNode.add(new HostAndPort("192.168.0.35", 6380));
            jedisClusterNode.add(new HostAndPort("192.168.0.36", 6380));

            /**
             参数说明
             Set jedisClusterNode：所有redis cluster节点信息，也可以只填写部分，应为客户端可以通过cluster slots发现
             int connectionTimeout：连接超时
             soTimeout：读写超时
             maxAttempts：重试次数，JedisCluster在连接的时候，如果出现连接错误，则会尝试随机连接一个节点，如果当期尝试的节点返回Moved重定向，jedis cluster会重新更新clots缓存。如果重试依然返回连接错误，会接着再次重试，当重试次数大于maxAttempts会报出Jedis ClusterMaxRedirectionsException(“to many Cluster redireciotns?”)异常
             password：集群密码
             poolConfig：连接池参数
             */
            jedis = new JedisCluster(jedisClusterNode, 1000, 1000, 5, AUTH, config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static JedisCluster getJedis() {
        return jedis;
    }
}