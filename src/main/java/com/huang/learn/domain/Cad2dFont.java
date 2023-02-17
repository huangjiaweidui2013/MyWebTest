package com.huang.learn.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author localuser
 * create at 2022/12/30 14:45
 * @description 文档字体
 */
@Data
@Builder
@TableName("cad_2d_font")
public class Cad2dFont {

    @TableId
    @TableField("doc_id")
    private Long docId;

    @TableField("fonts")
    private String fonts;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Integer createBy;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private Integer updateBy;
}
