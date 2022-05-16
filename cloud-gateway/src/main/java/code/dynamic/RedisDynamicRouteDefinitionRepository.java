package code.dynamic;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考InMemoryRouteDefinitionRepository spring cloud gateway 自带的维护路由仓库
 * https://www.cxyzjd.com/article/y532798113/109855717
 * redis动态路由
 */
@Slf4j
@Component
public class RedisDynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    //存储的的key
    private final static String KEY = "gateway_dynamic_route";


    @Resource
    private StringRedisTemplate redisTemplate;

    /**
     * 获取所有路由信息
     * Gateway启动的时候，会调用这个方法
     * RefreshRoutesEvent刷新事件后也会调用这个方法
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {

        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        try {
            redisTemplate.opsForHash().values(KEY).stream()
                    .forEach(routeDefinition -> routeDefinitions.add(JSONUtil.toBean(routeDefinition.toString(), RouteDefinition.class)));
        } catch (Exception e) {
            log.error("getRouteDefinitions !!!", e);
        }

        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * 新增
     *
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            redisTemplate.opsForHash().put(KEY, routeDefinition.getId(), JSONUtil.toJsonStr(routeDefinition));
            return Mono.empty();
        });
    }

    /**
     * 删除
     *
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (redisTemplate.opsForHash().hasKey(KEY, id)) {
                redisTemplate.opsForHash().delete(KEY, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("route definition is not found, routeId:" + routeId)));
        });
    }
}
