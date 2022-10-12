import com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ccy
 * @description
 * @time 2022/10/12 17:22
 */
@Slf4j
public class HystrixRequestVariableDefaultDemo {


    public static void main(String[] args) {
        ExecutorService pool1 = Executors.newFixedThreadPool(1);

        try {

            // 在当前线程下创建一个HystrixRequestContext对象
            HystrixRequestContext.initializeContext();

            // HystrixContextRunnable 是核心, 下面将分析源码:
//        HystrixContextRunnable runnable =
//                new HystrixContextRunnable(() -> System.out.println(variableDefault.get()));
//        // 用子线程执行任务
//        new Thread(runnable, "subThread").start();

            for (int i = 0; i < 10; i++) {
                // 创建HystrixRequestVariableDefault作为检索数据的key
                final HystrixRequestVariableDefault<String> varD1 = new HystrixRequestVariableDefault<>();
                final HystrixRequestVariableDefault<String> varD2 = new HystrixRequestVariableDefault<>();
                // 将<HystrixRequestVariableDefault,kitty>存储到当前线程的HystrixRequestContext中
                varD1.set("req=" + i);
                varD2.set("name=" + i);

                pool1.execute(new HystrixContextRunnable(() -> {
                    log.info(varD1.get()+"-------"+varD2.get());

                }));
            }

        } finally {
//            HystrixRequestContext.getContextForCurrentThread().shutdown();
        }

//

    }
}
