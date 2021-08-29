package code.feign;

import code.config.FeignConfig;
import code.dto.DateParamDTO;
import code.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author ccy
 * @description
 * @time 2018/11/21 下午3:00
 *
 * name:远程服务名，即spring.application.name配置的名称
 */
@FeignClient(name= "could-eureka-producer")
public interface RemoteService {
    @RequestMapping(value = "/hello")
    String hello(@RequestParam(value = "name") String name);

    @PostMapping("/dateParam")
    DateParamDTO dateParam(@RequestBody DateParamDTO dto);

    @GetMapping(value = "/object/hello")
    String objectHello(@RequestBody UserDTO userDTO);

    @GetMapping(value = "/object/hello")
    String objectHello(@RequestParam("username") String username,@RequestParam("msg")  String msg);

}
