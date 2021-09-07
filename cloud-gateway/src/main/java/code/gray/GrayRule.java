package code.gray;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ZoneAvoidanceRule 默认
 */
@Slf4j
@Component
public class GrayRule extends RoundRobinRule {

    public Server choose(ILoadBalancer lb, Object key) {
        List<Server> reachableServers = lb.getReachableServers();
        log.info("GrayRule#choose");
        return super.choose(lb, key);
    }
}
