package com.huang.controller;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.learn.common.AjaxResult;
import com.huang.learn.constant.Constants;
import com.huang.learn.domain.EmailPropertiesDTO;
import com.huang.learn.domain.IssueInfoDTO;
import com.huang.learn.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/10/31 9:07
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class SendEmailController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/send")
    public AjaxResult dealHtmlContent() throws JsonProcessingException, MessagingException {
        LocalDateTime now = LocalDateTime.now();
        String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        //组装附件
        String filePath = "D:\\壁纸";
//        File targetFile = new File(filePath + File.separator + "20230209_log.rar");
        String zipPath = "D:\\壁纸压缩\\";
        File zipFile = ZipUtil.zip(filePath, zipPath + dateTime + "_log.rar", Charset.defaultCharset(), false);

        Context context = new Context();
        List<String> filePaths = new ArrayList<>();
        IssueInfoDTO issueInfoDTO = IssueInfoDTO.builder()
                .issueId(Constants.SNOW_FLAKE.nextIdStr())
                .docId(Constants.SNOW_FLAKE.nextId())
                .handleId(new BigInteger(String.valueOf(Constants.SNOW_FLAKE.nextId())))
                .userId(100)
//                .source("APP_WEB")
                .name("获取捕获点崩溃")
                .time(now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .ip("127.0.0.1")
                .filePaths(filePaths)
//                .remark("编辑模式下，C++服务获取捕获点时崩溃")
                .build();
        String jsonString = JSON.toJSONString(issueInfoDTO, SerializerFeature.WriteDateUseDateFormat);
        Map<String, Object> params = JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>() {
        });
        context.setVariables(params);
        String process = templateEngine.process("issue_alert.html", context);
        EmailPropertiesDTO propertiesDTO = EmailPropertiesDTO.builder()
                .from(from)
                .to(new String[]{from})
                .subject("C++服务故障告警邮件")
                .content(process)
                .isHtml(true)
                .files(new File[]{zipFile})
                .build();
        emailService.sendEmailWithAttachment(propertiesDTO);
        return AjaxResult.success();
    }
}
