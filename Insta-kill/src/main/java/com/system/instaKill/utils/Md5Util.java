package com.system.instaKill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 *
 */

@Component
public class Md5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    private static final String salt = "1a2b3c4d";

    /**
     * 描述：
     *  第一层加密
     * @param inputPass 密码
     * @return MD5加密密码后的值
     */
    public static String inputPassToFromPass(String inputPass){
        String str = ""+ salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 描述：
     *  第二层加密
     * @param fromPass MD5加密密码后的值
     * @param salt 加密盐
     * @return 第二次MD5加密
     */
    public static String fromPassToDBPass(String fromPass,String salt){
        String str = ""+ salt.charAt(0)+salt.charAt(2)+fromPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }


    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
