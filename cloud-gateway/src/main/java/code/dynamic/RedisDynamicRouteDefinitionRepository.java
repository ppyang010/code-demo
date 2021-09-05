//package code.dynamic;
//
//import cn.hutool.json.JSONUtil;
//import com.google.common.collect.Lists;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 参考InMemoryRouteDefinitionRepository spring cloud gateway 自带的维护路由仓库
// https://www.cxyzjd.com/article/y532798113/109855717
// * redis动态路由
// */
//@Component
//public class RedisDynamicRouteDefinitionRepository implements RouteDefinitionRepository {
//
//    //存储的的key
//    private final static String KEY = "gateway_dynamic_route";
//
//    @Resource
//    private RedisUtils redisUtils;
//
//    /**
//     * 获取所有路由信息
//     * Gateway启动的时候，会调用这个方法
//     * RefreshRoutesEvent刷新事件后也会调用这个方法
//     *
//     * @return
//     */
//    @Override
//    public Flux<RouteDefinition> getRouteDefinitions() {
//        List<RouteDefinition> gatewayRouteEntityList = Lists.newArrayList();
//        redisUtils.hgets(KEY).stream().forEach(route -> {
//            RouteDefinition result = JSONUtil.toBean(route.toString(), RouteDefinition.class);
//            gatewayRouteEntityList.add(result);
//        });
//        return Flux.fromIterable(gatewayRouteEntityList);
//    }
//
//    /**
//     * 新增
//     * @param route
//     * @return
//     */
//    @Override
//    public Mono<Void> save(Mono<RouteDefinition> route) {
//        return route.flatMap(routeDefinition -> {
//            redisUtils.hset(KEY, routeDefinition.getId(), JSONUtil.toJsonStr(routeDefinition));
//            return Mono.empty();
//        });
//    }
//
//    /**
//     * 删除
//     * @param routeId
//     * @return
//     */
//    @Override
//    public Mono<Void> delete(Mono<String> routeId) {
//        return routeId.flatMap(id -> {
//            if (redisUtils.hHashKey(KEY, id)) {
//                redisUtils.hdel(KEY, id);
//                return Mono.empty();
//            }
//            return Mono.defer(() -> Mono.error(new NotFoundException("route definition is not found, routeId:" + routeId)));
//        });
//    }
//}
