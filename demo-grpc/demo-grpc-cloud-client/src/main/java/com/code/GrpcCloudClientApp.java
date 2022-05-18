package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ccy
 * @description
 * @time 2022/5/18 6:27 PM
 */
@EnableEurekaClient
@SpringBootApplication
public class GrpcCloudClientApp {
    public static void main(String[] args) {
        SpringApplication.run(GrpcCloudClientApp.class, args);
    }
}
