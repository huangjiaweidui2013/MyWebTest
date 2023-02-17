package com.huang.learn.controller;

import com.huang.learn.common.AjaxResult;
import com.huang.learn.config.MyPropertiesTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/7/25 12:01
 * @description 测试从配置文件中获取配置
 */
@Controller
@Slf4j
public class ConfigTestController {

    @Autowired
    private MyPropertiesTest myPropertiesTest;

    @Value("#{${my.web.test.strMap}}")
    private Map<String, String> strMap;

    @Value("#{${my.web.test.intMap}}")
    private Map<Integer, Integer> intMap;

    /**
     * 从yml中获取map类型配置项
     *
     * @return
     */
    @GetMapping("/config")
    @ResponseBody
    public AjaxResult getConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("class", myPropertiesTest.toString());
        map.put("objMap from property class", myPropertiesTest.getObjMap());
        map.put("strMap", this.strMap);
        map.put("intMap", this.intMap);
        map.put("workerId", myPropertiesTest.getWorkerId());

        return AjaxResult.success(map);
    }

}
