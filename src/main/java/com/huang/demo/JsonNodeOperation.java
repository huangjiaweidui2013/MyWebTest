package com.huang.demo;

import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/8/11 10:50
 * @description
 */
@Slf4j
public class JsonNodeOperation {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        String contentHtml = "<div _ngcontent-tef-c177=\"\" nz-col=\"\" nzspan=\"3\" class=\"ant-col ant-col-3\">" +
                "<img _ngcontent-tef-c177=\"\" src=\"/assets/image/zwcad.jpg\" alt=\"logo\" style=\"max-width: 70%;\"></div>";
        String s1 = HtmlUtils.htmlEscape(contentHtml);
        String s2 = HtmlUtil.escape(contentHtml);
        log.info("HtmlUtils: {}, HtmlUtil: {}", s1, s2);
        String unescape1 = HtmlUtils.htmlUnescape(s1);
        String unescape2 = HtmlUtil.unescape(s2);
        log.info("HtmlUtils.htmlUnescape: {}, HtmlUtil.unescape: {}", unescape1, unescape2);

    }


    public static void mergeJson() throws JsonProcessingException {
        String json1 = "{\"username\":\"tom\",\"company\":{\"companyName\":\"中华\",\"address\":\"北京\"},\"cars\":[\"奔驰\",\"宝马\"]}";
        String json2 = "{\"username\":\"jim\",\"company\":{\"com\":\"中兴\",\"address\":\"深圳\"},\"phone\":\"17891354565522\"}";

        Map<String, Object> map = new HashMap<>();
        JsonNode jsonNode1 = objectMapper.readTree(json1);
        JsonNode jsonNode2 = objectMapper.readTree(json2);
        merge(jsonNode1, map);
        merge(jsonNode2, map);
        map.forEach((k, v) -> {
            log.info("key: {},  value: {}", k, v);
        });

    }

    public static void merge(JsonNode node, Map<String, Object> map) {
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> next = iterator.next();
            String key = next.getKey();
            map.put(key, next.getValue().toString());
        }
    }
}
