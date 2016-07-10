package com.madioter.api.creater.common;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年11月13日 <br>
 */
public class StringUtil {

    /**
     * 判断字符串是否有值
     * @param str 字符串
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isBlank(char c) {
        String str = String.valueOf(c);
        return isBlank(str);
    }

    public static int leftBlanketCount(String item) {
        return getTagCount(item, Constants.LEFT_BLANKET);
    }

    public static int rightBlanketCount(String item) {
        return getTagCount(item, Constants.RIGHT_BLANKET);
    }

    public static int leftCurvesCount(String item) {
        return getTagCount(item, Constants.LEFT_CURVES);
    }

    public static int rightCurvesCount(String item) {
        return getTagCount(item, Constants.RIGHT_CURVES);
    }

    private static int getTagCount(String item, String tag) {
        item = item.trim();
        int count = 0;
        if (!item.contains(tag)) {
            return 0;
        } else {
            while (item.contains(tag)) {
                int index = item.indexOf(tag) + tag.length();
                item = item.substring(index);
                count++;
            }
        }
        return count;
    }

    public static boolean containsTag(String str, String tag) {
        while (str.contains(tag)) {
            int index = str.indexOf(tag);
            char start = str.charAt(index - 1);
            char end = str.charAt(index + tag.length());
            if (isBlank(start) && isBlank(end)) {
                return true;
            } else {
                str = str.substring(index + tag.length());
            }
        }
        return false;
    }
}
