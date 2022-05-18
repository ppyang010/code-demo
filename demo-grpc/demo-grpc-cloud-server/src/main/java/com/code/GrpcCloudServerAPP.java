package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ccy
 * @description
 * @time 2022/5/18 4:42 PM
 */
//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class GrpcCloudServerAPP {
    public static void main(String[] args) {
        SpringApplication.run(GrpcCloudServerAPP.class, args);
    }
}
