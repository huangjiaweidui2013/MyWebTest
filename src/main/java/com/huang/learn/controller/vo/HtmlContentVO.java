package com.huang.learn.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author localuser
 * create at 2022/11/18 17:58
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HtmlContentVO {
    private Long id;
    private String name;
    private String content;
}
