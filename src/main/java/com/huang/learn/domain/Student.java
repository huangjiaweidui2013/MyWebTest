package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author localuser
 * create at 2023/1/9 10:40
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test_student_collection") //指定要对应的文档名(表名）
public class Student {
    @Id
    private Long id;

    private String name;

    private Integer age;

    private Date createTime;
}
