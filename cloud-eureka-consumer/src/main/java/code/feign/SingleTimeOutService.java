package code.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ccy
 * @description name:远程服务名，即spring.application.name配置的名称
 * context id
 * 测试独立的超时配置
 * 测试过程中关闭了hystrix
 */
@FeignClient(name = "could-eureka-producer", contextId = "producer-long-timeout")
public interface SingleTimeOutService {

    @RequestMapping(value = "/slow/{sleepTime}")
    String slow2(@PathVariable("sleepTime") Integer sleepTime);

}
