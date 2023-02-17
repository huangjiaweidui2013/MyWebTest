package com.huang.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author localuser
 * create at 2022/8/5 17:30
 * @description 使用正则处理模板字符串
 */
@Slf4j
public class PatternReplace {
    public static final Pattern pattern = Pattern.compile("\\$\\{\\w+}");

    public static String processTemplate(String templateContent, Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = pattern.matcher(templateContent);
        while (matcher.find()) {
            String param = matcher.group();
            Object val = paramMap.get(param.substring(2, param.length() - 1));
            matcher.appendReplacement(sb, val == null ? "" : val.toString());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("money", String.format("%.2f", 10.155));
        map.put("point", 10);
        String message = processTemplate("您好${name}，晚上好！您目前余额：${money}元，积分：${point}， 用户名称：${name}。", map);
        log.info(message);
    }
}
