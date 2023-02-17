package com.huang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: huang lang
 * @description: web测试
 * 注意取消自动Multipart配置，否则可能在上传接口中拿不到file的值
 * @date: 2022/1/12 17:53
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
@MapperScan(basePackages = {"com.huang.*.mapper"})
@EnableScheduling
public class MyWebTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyWebTestApplication.class, args);
    }

}
