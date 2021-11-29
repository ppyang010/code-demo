package com.code;

import java.util.Arrays;

/**
 * @author ccy
 * @description
 * @time 2021/10/21 4:19 下午
 */
public class AnyTest {
    /**
     * <p>@description: 阿拉伯数字转中文数字(不带单位) </p>
     * <p>@method: numberConvertToBaseChinese </p>
     *
     * <p>@return java.lang.String </p>
     */
    public static String numberConvertToBaseChinese(int num) {
        StringBuffer buffer = new StringBuffer();
        char[] nums = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] baseChar = Integer.toString(num).toCharArray();
        for (char c : baseChar) {
            buffer.append(nums[c - 48]);
        }
        return buffer.toString();
    }

    /**
     * <p>@description: 阿拉伯数字转中文数字(带单位) </p>
     * <p>@method: numberConvertToChinese </p>
     *
     * <p>@return java.lang.String </p>
     */
    public static String numberConvertToChinese(int num) {
        char[] nums = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] units = {' ', '十', '百', '千', '万'};
        char[] baseChar = Integer.toString(num).toCharArray();
        char[] numChar = new char[baseChar.length];
        for (int i = 0; i < baseChar.length; i++) {
            numChar[i] = nums[baseChar[i] - 48];
        }
        char[] unit = new char[numChar.length];
        unit = Arrays.copyOf(units, unit.length);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < numChar.length; i++) {
            if (numChar[i] != nums[0]) {
                buffer.append(numChar[i]).append(unit[unit.length - i - 1]);
            } else {
                buffer.append(numChar[i]);
            }
        }
        int index = 0;
        while ((index = buffer.indexOf(nums[0] + String.valueOf(nums[0]))) > 0) {
            buffer.replace(index, index + 2, String.valueOf(nums[0]));
        }
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    public static void main(String[] args) {
        System.out.println(numberConvertToChinese(99));
        System.out.println(numberConvertToChinese(9));
        System.out.println(numberConvertToChinese(999));
        System.out.println(numberConvertToChinese(9999));
        System.out.println(numberConvertToChinese(99999));
        System.out.println(numberConvertToChinese(999999));
        System.out.println(numberConvertToChinese(9999999));
    }
}
