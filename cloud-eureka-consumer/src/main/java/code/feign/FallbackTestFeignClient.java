package code.feign;

import feign.Request;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "could-eureka-producer", fallbackFactory = FallbackTestFeignClient.FallbackTestFeignClientFallbackFactory.class)
public interface FallbackTestFeignClient {

    @GetMapping("/fallbackTest/error500")
    String error500();

    @GetMapping("/fallbackTest/errorTimeout")
    String errorTimeout();

    @GetMapping("/fallbackTest/errorThreadNotEnough")
    String errorThreadNotEnough();


    @Component
    @Slf4j
    public static class FallbackTestFeignClientFallbackFactory implements FallbackFactory<FallbackTestFeignClient> {

        @Override
        public FallbackTestFeignClient create(Throwable cause) {
            return new FallbackTestFeignClient() {

                @Override
                public String error500() {
                    log.info("fallback method error500");
                    return "fallback method error500";
                }

                @Override
                public String errorTimeout() {
                    log.info("fallback method errorTimeout");
                    return "fallback method errorTimeout";
                }

                @Override
                public String errorThreadNotEnough() {
                    log.info("fallback method errorThreadNotEnough");
                    return "fallback method errorThreadNotEnough";
                }
            };
        }
    }
}
