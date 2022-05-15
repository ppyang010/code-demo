package com.code.demo;

import cn.hutool.json.JSONUtil;
import com.code.data.PeopleEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jdk.nashorn.internal.objects.annotations.Where;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ccy
 * @description
 * @time 2022/3/24 5:35 PM
 */
@Slf4j
public class GuavaDemo1 {


    private static LoadingCache<String, Optional<PeopleEntity>> GuavaCache = CacheBuilder.newBuilder()
            .maximumSize(30)
            .expireAfterWrite(5, TimeUnit.MINUTES)

            .refreshAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Optional<PeopleEntity>>() {

                @Override
                public Optional<PeopleEntity> load(String key) {
                    log.info("threadName={} 加载数据",Thread.currentThread().getName());
                    return getDataLoad(key);
                }


                private Optional<PeopleEntity> getDataLoad(String key) {
                    log.info("getDataLoad");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PeopleEntity entity = new PeopleEntity();
                    entity.setName(key);
                    Random random = new Random();
                    entity.setAge(random.nextInt(100));
                    return Optional.ofNullable(entity);
                }





            });


    public static void main(String[] args) {
        //启动时没有缓存,所有请求线程都会阻塞等待,但是只有一个请求线程执行加载逻辑
        //定时刷新缓存的时候会阻塞一个线程,其他线程不阻塞返回旧值
        ThreadPoolExecutor QUERY_EXECUTOR = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            QUERY_EXECUTOR.execute(() -> {
                while (true) {
                    try {
                        long start = System.currentTimeMillis();
                        log.info("threadName={} getGuavaCache start i={}", Thread.currentThread().getName(), finalI);
                        Optional<PeopleEntity> test = GuavaCache.get("test");
                        PeopleEntity peopleEntity = test.orElse(null);
                        long end = System.currentTimeMillis();
                        log.info("threadName={}getGuavaCache end i={},res={},cost={}",
                                Thread.currentThread().getName(), finalI, peopleEntity.getAge(), end - start);
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });


        }
        while (true){

        }
//        QUERY_EXECUTOR.shutdown();
//        CACHE_EXECUTOR.shutdown();

    }


}
