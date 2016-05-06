package com.yc.BaiSiBuDeJie.utils;

import android.util.Patterns;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 常用验证方法
 */
public class ValidatesUtil {

    public static final String EMAIL_EMPTY = "empty";
	public static final String EMAIL_INVALID = "invalid";
	public static final String EMAIL_VALID = "valid";

	public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
	
	public static boolean isEmpty(String[] arrays) {
        return arrays == null || arrays.length == 0;
    }
	
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Map<?, ?> maps) {
        return maps == null || maps.size() == 0;
    }

    public static boolean isFlagContain(int sourceFlag, int compareFlag) {
        return (sourceFlag & compareFlag) == compareFlag;
    }

    public static boolean isNull(Object o) {
        return o == null ? true : false;
    }

    public static String defaultValue(int value){
        if(value == 0){
            return "";
        }
        return value + "";
    }

	/**
	 * 验证邮箱格式
	 */
	public static String verifyEmail(String email) {
		String result = EMAIL_VALID;
		email = email.trim();
		if(isEmpty(email)) {
			result = EMAIL_EMPTY;
		} else {
			Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
			if(!matcher.matches()) {
				result = EMAIL_INVALID;
			}			
		}
		return result;
	}

    /**
     * 判断输入字符是否为中文
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

}
