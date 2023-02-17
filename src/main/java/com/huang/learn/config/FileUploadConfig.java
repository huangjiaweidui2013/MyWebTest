package com.huang.learn.config;

import com.huang.learn.resolver.CustomMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author: huang lang
 * @description:
 * @date: 2022/2/9 14:04
 */
@Configuration
public class FileUploadConfig {

    /**
     * 注入自定义的文件上传处理类
     *
     * @return MultipartResolver
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
    }
}
