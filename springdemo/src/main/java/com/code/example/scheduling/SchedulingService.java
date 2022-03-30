package com.code.example.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author ccy
 * @description
 * @time 2019/3/15 下午4:46
 */
@Component
@Slf4j
public class SchedulingService {
    private static volatile boolean enable = true;

    @Resource
    @Qualifier("okHttpRestTemplate")

    private RestTemplate restTemplate;


    //    @Scheduled(cron = "0/10 * * * * ?")
//    @Scheduled(cron = "0 0/5 * * * ?")
    public void schedulingA() {
        log.info("schedulingA method run thread name={}", Thread.currentThread().getName());
        while (true) {
            String str = restTemplate.getForObject("http://www.dxy.cn/japi/platform/journal/change/data_ready", String.class);
            log.info("restTemplate result = {}", str);
            if ("false".equals(str)) {
                return;
            }
        }


//        if (enable) {
//            enable = false;
//            log.info("enable = false");
//            ArrayList<Object> objects = Lists.newArrayList();
//            while (true) {
//                try {
//                    int size = 3 * 1024 * 1024;
//                    ByteBuffer temporaryDirectBuffer = Util.getTemporaryDirectBuffer(size);
//                    objects.add(temporaryDirectBuffer);
//                    log.info("create ByteBuffer");
//                    Thread.sleep(5000);
//                } catch (Exception e) {
//                    enable = true;
//                    log.info("enable = true");
//                    e.printStackTrace();
//                    break;
//                }
//
//            }
//
//        }


    }
}
