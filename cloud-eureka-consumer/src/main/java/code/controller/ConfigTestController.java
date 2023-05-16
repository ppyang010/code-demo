package code.controller;

import code.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ccy
 * spring config 配置中心
 */
@RestController
public class ConfigTestController {
    @Value("${form.remark}")
    private String remark;

    @Value("${form.profile}")
    private String profile;

    @GetMapping("/config/test")
    public UserDTO test(HttpServletResponse response) {
        System.out.println("profile="+profile);
        System.out.println("remark="+remark);
        UserDTO userDTO = new UserDTO();
        userDTO.setMsg("hello");
        userDTO.setUsername("ccy");
        return userDTO;
    }
}
