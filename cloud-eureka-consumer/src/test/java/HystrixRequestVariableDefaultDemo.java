import cn.hutool.core.util.RandomUtil;
import com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ccy
 * @description
 * @time 2022/10/12 17:22
 */
@Slf4j
public class HystrixRequestVariableDefaultDemo {

    public static ExecutorService pool1 = Executors.newFixedThreadPool(10);
    public static ExecutorService pool2 = Executors.newFixedThreadPool(10);


    public static void main(String[] args) {

        try {

            // 在当前线程下创建一个HystrixRequestContext对象
//            HystrixRequestContext.initializeContext();



            for (int i = 0; i < 30; i++) {
                // 将<HystrixRequestVariableDefault,kitty>存储到当前线程的HystrixRequestContext中

                pool1.execute(new HystrixContextRunnable(() -> {
                    sendRequest();
                }));
            }
            Scanner input = new Scanner(System.in);
            String val = null;       // 记录输入度的字符串
            do{
                System.out.print("请输入：\n");
                val = input.next();       // 等待输入值
                System.out.println("您输入的是："+val);
            }while(!val.equals("#"));   // 如果输入的值不版是#就继续输入

        } finally {
//            HystrixRequestContext.getContextForCurrentThread().shutdown();
            System.exit(9);
        }

//9

    }


    public static void sendRequest() {
        int x = RandomUtil.randomInt(0, 999);
        MyRequestContext.setFlag("req=" + x, "flag=" + x);
        log.info("sendRequest----"+MyRequestContext.getFlag());
        pool2.execute(new HystrixContextRunnable(() -> {
            dealRequest();
        }));
    }

    public static void dealRequest() {
        try{
            log.info("dealRequest----"+MyRequestContext.getFlag());

        }finally {
            MyRequestContext.removeFlag();
        }
    }


    public static class MyRequestContext {

        private static final HystrixRequestVariableDefault<String> var_flag1 = new HystrixRequestVariableDefault<>();
        private static final HystrixRequestVariableDefault<String> var_flag2 = new HystrixRequestVariableDefault<>();

        /**
         *
         */
        public static String getFlag() {
            if (HystrixRequestContext.isCurrentThreadInitialized()) {
                return var_flag1.get() + "----" + var_flag2.get();
            }
            return "-1";
        }


        public static void setFlag(String flag1, String flag2) {
            if (StringUtils.isBlank(flag1) || StringUtils.isBlank(flag2)) {
                return;
            }

            // 需要先调用此方法进行初始化 要不然下面set会报空指针
            if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                HystrixRequestContext.initializeContext();
            }
            var_flag1.set(flag1);
            var_flag2.set(flag2);
        }

        /**
         *
         */
        public static void removeFlag() {
            if (HystrixRequestContext.isCurrentThreadInitialized()) {
                HystrixRequestContext.getContextForCurrentThread().shutdown();
            }
        }
    }
}
