package cn.zucc.searchfinal.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseUtils {
    public static final Pattern CHINESE_PREFIX_PATTERN = Pattern.compile("^[\u4e00-\u9fa5]+");

    /**
     * 提取中文前缀
     * @param text
     * @return
     */
    public static String getChinesePrefix(String text) {
        Matcher m = CHINESE_PREFIX_PATTERN.matcher(text);
         System.out.println(m);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println(getChinesePrefix("中华人民nihaoa"));
    }
}
