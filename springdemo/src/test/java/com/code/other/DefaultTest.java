package com.code.other;

import java.math.BigDecimal;

/**
 * 默认测试代码
 * 未确定分类的测试/验证代码都可以用这个
 *
 * @author ccy
 * @description
 * @time 2022/11/22 18:02
 */
public class DefaultTest {

    public static void main(String[] args) {
        BigDecimal lixi = new BigDecimal("1.03");
        BigDecimal benjin = new BigDecimal("10000");
        int year = 30;
        for (int i = 0; i < year; i++) {
            benjin = benjin.multiply(lixi);
        }
        System.out.println(benjin.toString());
    }
}
