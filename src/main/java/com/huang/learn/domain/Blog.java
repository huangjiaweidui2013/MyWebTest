package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author localuser
 * create at 2023/1/9 16:44
 * @description 博客实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    /**
     * 博客id
     */
    @Id
    private Long id;

    /**
     * 博客用户ID
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 文章
     */
    private Article article;

}
