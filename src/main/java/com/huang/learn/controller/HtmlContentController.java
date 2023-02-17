package com.huang.learn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.learn.common.AjaxResult;
import com.huang.learn.controller.vo.HtmlContentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author localuser
 * create at 2022/11/18 17:56
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/html")
public class HtmlContentController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/deal")
    public AjaxResult dealHtmlContent(@RequestBody HtmlContentVO vo) throws JsonProcessingException {
        log.info("接收到的请求参数：{}", objectMapper.writeValueAsString(vo));
        return AjaxResult.success();
    }
}
