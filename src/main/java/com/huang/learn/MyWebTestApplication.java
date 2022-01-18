package com.huang.learn;

import com.huang.learn.resolver.CustomMultipartResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author: huang lang
 * @description: web测试
 * 注意取消自动Multipart配置，否则可能在上传接口中拿不到file的值
 * @date: 2022/1/12 17:53
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
public class MyWebTestApplication extends SpringBootServletInitializer {

    /**
     * 注入自定义的文件上传处理类
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyWebTestApplication.class, args);
    }

}
