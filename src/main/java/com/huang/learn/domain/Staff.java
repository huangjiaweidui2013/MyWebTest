package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author localuser
 * create at 2023/1/10 11:08
 * @description 职员对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Staff {
    @Id
    private Long id;

    private String name;

    // DBRef 引用了另一个文档，该字段存储引用文档的 _id（ObjectId）
    @Field("dept_id")
    @DBRef
    private Dept dept;

    //内嵌的子文档
    private List<Skill> skills;

    //瞬时属性不会持久化
    @Transient
    private String phone;
}
