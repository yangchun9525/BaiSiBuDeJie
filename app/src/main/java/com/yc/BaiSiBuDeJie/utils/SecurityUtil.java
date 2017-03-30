package com.yc.baisibudejie.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yangchun on 2015/5/28.
 * 常用安全工具类
 */
public class SecurityUtil {

    /**
     * 对input进行sha1加密处理
     * @param input
     * @return
     */
    public static String getSha1(String input) {
        MessageDigest digest;
        byte[] result = null;

        try {
            digest = MessageDigest.getInstance("SHA1");
            result = digest.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        if (result != null) {
            for(byte b : result) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 获取input的md5摘要
     * @param input
     * @return
     */
    public static String getMD5(String input) {

        MessageDigest digest;
        byte[] result = null;
        char[] ch = "0123456789abcdef".toCharArray();

        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes());
            // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            result = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder buf = new StringBuilder();
        if (result != null) {
            // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            for(byte b : result){
                // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                buf.append(ch[b >>> 4 & 0xf]);
                // 取字节中低 4 位的数字转换
                buf.append(ch[b & 0xf]);
            }
        }
        // 换后的结果转换为字符串
        return buf.toString();

    }

}
