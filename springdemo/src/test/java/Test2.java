import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author ccy
 * @description
 * @time 2021/12/13 4:02 下午
 */
public class Test2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s= "{\"success\":true,\"code\":200,\"responseTime\":0,\"message\":null,\"data\":{\"target_title\":\"\",\"target_content\":\"æµ\u008Bè¯\u0095ï¼\u008C***\",\"result\":[{\"position\":\"content\",\"word\":\"ä¹ è¿\u0091å¹³\",\"rank\":\"2\",\"start_offset\":3,\"end_offset\":5}]}}";
        String s1 = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        System.out.println(s1);
    }
}
