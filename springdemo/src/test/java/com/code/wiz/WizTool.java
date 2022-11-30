package com.code.wiz;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ccy
 * @description
 * @time 2022/11/29 16:34
 */
public class WizTool {

    public static void main(String[] args) {
//        genWizUriFromBrowser()
        genWizUri("14854e82-c3e2-4fb6-8969-0b7456fac397");
    }


    /**
     * @param url 浏览器url栏中url
     * @return
     */
    public static String genWizUriFromBrowser(String url) {
        //https://www.wiz.cn/wapp/folder/d80cc495-0821-48fc-87d8-265551858bc2?docGuid=508d59bd-893d-4243-9c64-dd5cf5f54b2b&c=%2F2019%2Fseata%2F
        Map<String, Object> parameter = getParameter(url);
        System.out.println(JSONUtil.toJsonStr(parameter));
        String docGuid = (String) parameter.getOrDefault("docGuid", "");
        return genWizUri(docGuid);
    }


    /**
     * @param docGuid 文章id
     * @return
     */
    public static String genWizUri(String docGuid) {
        if (StrUtil.isBlank(docGuid)) {
            return "docGuid 为空";
        }
        String template = "wiz://open_document?guid={}&kbguid=&private_kbguid=d80cc495-0821-48fc-87d8-265551858bc2";

        String format = StrUtil.format(template, docGuid);
        System.out.println( format);
        return format;
    }


    public static Map<String, Object> getParameter(String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (int i = 0; i < keyValues.length; i++) {
                    String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                    String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
