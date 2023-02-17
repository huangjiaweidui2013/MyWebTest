package com.huang.learn.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文档外部参照映射对象 cad_2d_xref
 *
 * @author huang lang
 * @date 2022-06-10
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cad2dXref extends BaseEntity {

    private static final long serialVersionUID = -3443901461355543509L;
    
    /**
     * 文档id
     */
    private Long docId;

    /**
     * 句柄id
     */
    private Long xrefHandleId;

    /**
     * 文档内部的参照文件路径
     */
    private String refPath;

    /**
     * 参照文档id
     */
    private Long refDocId;

    /**
     * 是否有效，0 -未找到， 1-已加载，2-未参照（预留），3-已卸载（预留））
     */
    private Integer status;

}
