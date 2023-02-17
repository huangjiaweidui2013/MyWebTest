package com.huang.learn.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/7/26 15:59
 * @description
 */
@Slf4j
public class TestController {
    public static final long DATA_CENTER_ID = IdUtil.getDataCenterId(31L);

    public static final long WORKER_ID = IdUtil.getWorkerId(DATA_CENTER_ID, 31L);

    public static final Snowflake SNOW_FLAKE = IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID);


    public static void main(String[] args) throws IOException, TemplateException {
//        long dataCenterId = IdUtil.getDataCenterId(31L);
//        long workerId = IdUtil.getWorkerId(dataCenterId, 31L);
//        Snowflake snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
//        long id = snowflake.nextId();
//        log.info(String.valueOf(dataCenterId));
//        log.info(String.valueOf(workerId));
//        log.info(String.valueOf(id));

        log.info(String.valueOf(DATA_CENTER_ID));
        log.info(String.valueOf(WORKER_ID));
        log.info(SNOW_FLAKE.nextIdStr());

        Locale locale = Locale.getDefault();
        log.info(locale.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("money", 10.155);
        map.put("point", 10);
        StringWriter result = new StringWriter();
        Configuration cfg = new Configuration(Configuration.getVersion());
        Template template = new Template("strTpl",
                "您好${name}，晚上好！您目前余额：${money?string(\"#.##\")}元，积分：${point}分，姓名：${name}。",
                cfg);
        template.process(map, result);
        log.info(result.toString());


        String[] strings = new String[]{"wwe", "dff", "SSS"};
        System.out.println(Arrays.toString(strings));

    }

}
