package com.huang.demo.qyapi;

import com.huang.learn.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author localuser
 * create at 2023/2/7 17:36
 * @description 通过企业微信群里面手动建立的机器人向群里发送消息
 */
@Slf4j
public class QiYeWeiXinBot {

    public static void main(String[] args) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=5cdbaef4-5a52-4d53-9e9b-ab38b13de59e";

        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String demoStr = "{" +
                "    \"msgtype\": \"markdown\",\n" +
                "    \"markdown\": {\n" +
                "        \"content\": \"<font color=\\\"warning\\\">${msg}</font>\\n\n" +
                "         >名称:  <font color=\\\"comment\\\">${name:-}</font>\n" +
                "         >id:  <font color=\\\"comment\\\">${issueId:-}</font>\n" +
                "         >时间:  <font color=\\\"comment\\\">${time:-}</font>\n" +
                "         >备注:  <font color=\\\"comment\\\">${remark:-}</font>\"\n" +
                "    }\n" +
                "}\n";
        Map<String, Object> params = new HashMap<>();
        params.put("msg", "Cloud2D服务出现异常问题，请查看问题告警邮件获取更多信息。");
        params.put("time", dateTime);
        params.put("name", "获取捕获点崩溃");
        params.put("issueId", Constants.SNOW_FLAKE.nextIdStr());
//        params.put("remark", "编辑模式下，C++服务获取捕获点时崩溃");
        StringSubstitutor substitutor = new StringSubstitutor(params);
        String replace = substitutor.replace(demoStr);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity2 = new HttpEntity<>(replace, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity2, String.class);
        log.info("结果：{}", response);

    }
}
