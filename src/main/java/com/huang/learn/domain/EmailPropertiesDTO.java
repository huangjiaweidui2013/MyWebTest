package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author localuser
 * create at 2023/2/14 16:31
 * @description 邮件基本参数设置DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailPropertiesDTO {

    private String from;

    private String[] to;

    private String[] cc;

    private String subject;

    private String content;

    private File[] files;

    private boolean isHtml;

}
