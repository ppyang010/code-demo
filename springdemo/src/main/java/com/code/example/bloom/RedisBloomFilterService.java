package com.code.example.bloom;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

/**
 * SimpleBloomFilter
 *
 * @author zhangfuhao
 * @date 2023年08月30日
 */
@Slf4j
@Component
public class RedisBloomFilterService {
    private RedisTemplate<String, Object> redisTemplate;

    // 预估插入量
    private long expectedInsertions = 200_0000L;

    // 可接受的错误率
    private double fpp = 0.01F;

    public RedisBloomFilterService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 评估预算 所需bit数
    private long numBits = optimalNumOfBits(expectedInsertions, fpp);
    private int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);

    // 计算 所需bit数
    private long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    // 计算 所需hash函数个数
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    // put元素
    public void put(String where, String key) {
        long[] indexs = getIndexs(key);
        List<Object> pipelinedResultList = redisTemplate.executePipelined(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (long index : indexs) {
                    redisTemplate.opsForValue()
                            .setBit(where, index, true);
                }
                // 返回null即可，因为返回值会被管道的返回值覆盖，外层取不到这里的返回值
                return null;
            }
        });
    }

    // 判断元素是否存在
    public boolean isExist(String where, String key) {
        long[] indexs = getIndexs(key);
        List<Object> pipelinedResultList = redisTemplate.executePipelined(new SessionCallback<Boolean>() {
            @Override
            public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
                for (long index : indexs) {
                    redisTemplate.opsForValue()
                            .getBit(where, index);
                }
                // 返回null即可，因为返回值会被管道的返回值覆盖，外层取不到这里的返回值
                return null;
            }
        });
        return pipelinedResultList.stream()
                .allMatch(x -> (Boolean) x);
    }

    public void delete(String where) {
        redisTemplate.delete(where);
    }

    /**
     * 计算key 对应在bit数组中的下标
     * 经过 numHashFunctions 个hash函数计算后，返回numHashFunctions个位置下标
     *
     * @param key
     * @return
     */
    private long[] getIndexs(String key) {
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;

        long[] result = new long[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            long combinedHash = hash1 + i * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            result[i] = combinedHash % numBits;
        }
        return result;
    }


    // murmur哈希函数
    private static long hash(String key) {
        Charset charset = Charset.forName("UTF-8");
        return Hashing.murmur3_128()
                .hashObject(key, Funnels.stringFunnel(charset))
                .asLong();
    }
}
