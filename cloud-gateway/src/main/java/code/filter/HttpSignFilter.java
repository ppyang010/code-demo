package code.filter;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.SecureUtil;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * http数字签名过滤器
 * 参考链接https://blog.csdn.net/u011521890/article/details/55506716
 * 请求方参数
 * a=xxx
 * b=xxx
 * c=xxx
 * random=8位random字符串
 * timestamp=时间戳 不参与签名 判断是否在5分钟内
 * sign=由a,b,c,random 按ascii码排序 在使用sha1编码
 *
 * @author ccy
 */
//@Component
public class HttpSignFilter implements GlobalFilter, Ordered {

    /**
     * 签名加盐
     * todo:放入配置中心
     */
    private static final String SALT = "abcxccy";
    /**
     * 允许多久时间内的请求
     */
    private static final long TIME_LIMIT = 60 * 5 * 1000;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        MultiValueMap<String, String> queryParams = request.getQueryParams();

        //1.判断请求是否在5分钟内
        if (!compareTimestamp(queryParams.getFirst("timestamp"))) {
            String resText = "请求超时";
            return write(exchange, response, resText);
        }
        //2.开始验签
        //因为要把请求参数按ascii码排序
        TreeMap<String, String> treeMap = new TreeMap<>();
        queryParams.forEach((k, v) -> treeMap.put(k, v.get(0)));
        treeMap.remove("sign");
        treeMap.remove("timestamp");
        //客户端传过来的sign
        String clientSign = queryParams.getFirst("sign");
        //验证签名
        boolean isAllow = checkSign(treeMap, clientSign);
        if (!isAllow) {
            //验签不通过
            String resText = "签名错误";
            return write(exchange, response, resText);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> write(ServerWebExchange exchange, ServerHttpResponse response, String resText) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(resText.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }

    /**
     * 进行签名验证
     *
     * @param treeMap    服务端进行签名计算的参数列表
     * @param clientSign 客户端传过来的签名值
     * @return true 验证通过
     */
    private boolean checkSign(TreeMap<String, String> treeMap, String clientSign) {
        StringBuilder paramStr = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if (paramStr.length() > 0) {
                paramStr.append('&');
            }
            paramStr.append(entry.getKey());
            paramStr.append('=');
            paramStr.append(entry.getValue());
        }
        //服务端计算出来的sign
        String serverSign = SecureUtil.sha1(paramStr.toString());
        System.out.println("init_interface paramStr.toString()[" + paramStr.toString() + "]");
        System.out.println(StringFormatter.format("客户端传入签名:{%s},后端计算签名:{%s}", clientSign, serverSign).getValue());
        return serverSign.equals(clientSign) && treeMap.get("random").length() == 8;
    }

    /**
     * 校验时间戳是否在合理时差范围内
     */
    private boolean compareTimestamp(String timestamp) {
        if (NumberUtil.isNumber(timestamp)) {
            return (System.currentTimeMillis()/1000 - Long.parseLong(timestamp)) <= TIME_LIMIT;
        }
        return false;
    }


    @Override
    public int getOrder() {
        return -200;
    }

    public static void main(String[] args) {
        TreeMap<String, String> mockMap = new TreeMap<>();
        mockMap.put("a", "a");
        mockMap.put("b", "b");
        mockMap.put("c", "c");
        mockMap.put("random", "12345678");
        StringBuilder paramStr = new StringBuilder();
        for (Map.Entry<String, String> entry : mockMap.entrySet()) {
            if (paramStr.length() > 0) {
                paramStr.append('&');
            }
            paramStr.append(entry.getKey());
            paramStr.append('=');
            paramStr.append(entry.getValue());
        }
        //服务端计算出来的sign
        String serverSign = SecureUtil.sha1(paramStr.toString());
        System.out.println("init_interface paramStr.toString()[" + paramStr.toString() + "]");
        System.out.println("sign=" + serverSign);
    }
}
