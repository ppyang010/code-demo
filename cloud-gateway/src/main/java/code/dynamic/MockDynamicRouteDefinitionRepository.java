package code.dynamic;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedMap;

/**
 * 参考InMemoryRouteDefinitionRepository spring cloud gateway 自带的维护路由仓库
 * 自定义动态路由
 */
//@Component
public class MockDynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    /**
     * 存放所有路由信息
     */
    private final Map<String, RouteDefinition> routes =new ConcurrentHashMap<>();

    /**
     * 从数据源加载路由
     */
    @PostConstruct
    public void loadRoutes(){
        //mock 模拟从数据源加载


    }
    /**
     * 获取所有路由信息
     * Gateway启动的时候，会调用这个方法
     * RefreshRoutesEvent刷新事件后也会调用这个方法
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }



    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (StringUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(
                    new NotFoundException("RouteDefinition not found: " + routeId)));
        });

    }
}
