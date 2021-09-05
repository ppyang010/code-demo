package code;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {
    @GetMapping("/index")
    public Mono<String> defaultMethod(){
        return Mono.just("index");
    }
}
