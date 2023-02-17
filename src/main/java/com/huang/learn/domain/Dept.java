package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author localuser
 * create at 2023/1/10 11:08
 * @description 部门对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Dept {
    @Id
    private Long id;

    private String title;

    private String loc;

}
