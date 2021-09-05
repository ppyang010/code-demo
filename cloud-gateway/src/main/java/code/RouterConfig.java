package code;

import code.filter.RequestTimeFilter;
import code.filter.TokenFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ccy
 */
@Configuration
public class RouterConfig {

    /**
     * 使用代码方式配置路由
     *
     * @return
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/bili")
                        .uri("http://www.bilibili.com:80/").id("bili_route"))
                .route(r -> r
                        .path("/default/**")
                        .filters(f -> f.addRequestHeader("head", "hello head")
                                .addRequestParameter("param", "hello")
                                .addResponseHeader("respParam", "hello"))
                        .uri("forward:/default").id("default_route"))
                .route(r -> r
                        .path("/target/**")
                        .filters(f -> f.addRequestHeader("head", "hello head")
                                .addRequestParameter("param", "hello")
                                .addResponseHeader("respParam", "hello")
                                .hystrix(config -> config
                                        .setName("target_route_fallback")
                                        .setFallbackUri("forward:/fallback")))
                        .uri("http://127.0.0.1:7720/gateway/target").id("target_route"))
                .route(r -> r
                        //lb+hystrix
                        .path("/code_lb/**")
                        .filters(f -> f.stripPrefix(1).addRequestHeader("head", "这是code_lb")
                                .addRequestParameter("isSleep", "true")
                                .hystrix(config -> config.setName("target_route_fallback").setFallbackUri("forward:/fallback")))
                        .uri("lb://could-eureka-consumer").id("lb_target_route"))
                .route(r -> r
                        .path("/my_gateway_filter/**")
                        .filters(f -> f.stripPrefix(1)
                                .addRequestHeader("head", "my_gateway_filter")
                                .filter(new RequestTimeFilter()))
                        .uri("lb://could-eureka-consumer").id("my_gateway_filter_route"))
                .build();
    }

}
