package com.code;

import com.code.annotation.enable.EnableBean;
import com.code.auth.FactoryBean.BeanLife;
import com.code.auth.config.DemoConfig;
import com.code.auth.config.ShiroConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * @author ccy
 */
@Slf4j
@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@EnableBean
@Import({ShiroConfig.class, DemoConfig.class})
public class SpringDemoApplication {

    public static void main(String[] args) {
//        new AnnotationConfigApplicationContext();
//        SpringApplication.run(SpringDemoApplication.class, args);
        SpringApplication springApplication = new SpringApplication(SpringDemoApplication.class);
        //设置为 非 web 应用
        //springApplication.setWebEnvironment(false);
        ConfigurableApplicationContext context = springApplication.run(args);
        log.info("spring boot 启动完成 {}", Thread.currentThread().getName());
        context.getBean(BeanLife.class);

        //从spring中获取bean
//        MyAop bean = context.getBean(MyAop.class);
        //进行处理
//        bean.findOne(1);
//        System.out.println("end");
//		System.out.println(context.getBean(SpringDemoApplication.class));
//		// 输出当前 Spring Boot 应用的 ApplicationContext 的类名
//		System.out.println("当前 Spring 应用上下文的类：" + context.getClass().getName());

    }

    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        System.out.println("当前webServer实现类为：" + event.getWebServer().getClass().getName());

    }

}
