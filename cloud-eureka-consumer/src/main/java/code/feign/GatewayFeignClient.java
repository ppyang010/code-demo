package code.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ccy
 * @description
 * @time 2018/11/21 下午3:00
 * <p>
 * name:远程服务名，即spring.application.name配置的名称
 */
@FeignClient(name = "could-gateway")
public interface GatewayFeignClient {

    @GetMapping("/default")
    String defaultMethod();
}
