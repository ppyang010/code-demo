package code.controller.rest;

import code.feign.FallbackTestFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试触发fallback的方式
 * 测试会触发进入fallback 方法的情况
 * 1.服务提供者返回500   进入fallback
 * 2.接口调用超时   进入fallback
 * 3.熔断器打开  会进入fallback
 * 4.资源不足
 * 4.1线程池  线程资源不足且任务队列满->资源不足->进入线程池的拒绝策略->拒绝策略就是进入fallback
 * 4.2信号量  信号量不够会进入fallback
 */
@RestController
@RequestMapping("/fallbackTest")
@Slf4j
public class FallbackTestController {

    private static ThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
            .namingPattern("scheduled-worker-thread-%d").build();

    private static Executor executor = new ThreadPoolExecutor(4, 4,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), basicThreadFactory);

    private AtomicInteger times = new AtomicInteger(1);
    @Autowired
    private FallbackTestFeignClient fallbackTestFeignClient;


    @GetMapping("/error500")
    public String error500() {
        return fallbackTestFeignClient.error500();
    }

    @GetMapping("/errorTimeout")
    public String errorTimeout() {
        return fallbackTestFeignClient.errorTimeout();
    }

    @GetMapping("/errorThreadNotEnough")
    public String errorThreadNotEnough() {
        return fallbackTestFeignClient.errorThreadNotEnough();
    }

//    @Scheduled(cron = "0/2 * * * * ? ")
    public void circuitBreakerScheduled() {
        for (int i = 0; i < 10; i++) {
            log.info("circuitBreakerScheduled start time={}", times.getAndIncrement());
            executor.execute(() -> {
                String res = fallbackTestFeignClient.errorTimeout();
                log.info(res);
            });
        }
    }

    @GetMapping("batchRequest")
    public void batchRequest(int num) {
        for (int i = 0; i < num; i++) {
            log.info("circuitBreakerScheduled start time={}", times.getAndIncrement());
            executor.execute(() -> {
                String res = fallbackTestFeignClient.errorTimeout();
                log.info(res);
            });
        }
    }
}
