package com.code;

import com.code.annotation.enable.EnableBean;
import com.code.auth.config.ShiroConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


/**
 * @author ccy
 */
@SpringBootApplication
@EnableBean
@Import(ShiroConfig.class)
public class SpringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);
	}
}
