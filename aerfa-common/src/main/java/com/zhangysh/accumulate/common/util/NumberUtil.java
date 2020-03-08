package com.zhangysh.accumulate.common.util;

import java.util.Random;

/**
 * 数字相关工具类
 *
 * @author zhangysh
 * @date 2020年03月08日
 */
public class NumberUtil {

    /**
     * 获取指定范围的整数
     * @param min 范围最小值
     * @param max 范围最大值
     ***/
    public static int getRangeInt(int min,int max){
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * 将double转换为大写人名币
     *
     * @param numbervalue
     *            要转换的数据，最大为九千九...亿...
     * @return 返回大写的人名币字符串,精确到分,不精确到厘 <li>返回的对象格式:壹仟亿零伍仟圆肆角零分;如果是0就返回零圆整</li>
     */
    public static String DoubleNumberToRMB(double numbervalue) {
        if(numbervalue==0){
            return "零圆整";
        }
        char[] hunit = { '拾', '佰', '仟' };
        char[] vunit = { '万', '亿' };
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
        long midVal = (long) (numbervalue * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串
        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            // rail.charAt(0) - '0'进行运算过后就是数字
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0′表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) {
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志第一次出现0
                    zero = digit[0];
                    // idex==0表示是段位的结尾;zeroSerNum表示这个段位有数字,不全部为0
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];// 单位:万,亿
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];// 单位:拾,佰,仟
            // 段位大于0,段内位置是末尾
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }
        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

    public static void main(String args[]){
        for (int i=0;i<100;i++){
            System.out.println(getRangeInt(1,10));
        }

    }
}
