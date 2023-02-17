package com.huang.learn.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 文档外部参照lmf文件对象 cad_2d_layout_xref
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
public class Cad2dLayoutXref extends BaseEntity {

    private static final long serialVersionUID = 7364732118891764129L;

    /**
     * 文档id
     */
    private Long docId;

    /**
     * 句柄id
     */
    private Long handleId;

    /**
     * 外部参照句柄id,
     */
    private String xrefIds;

    /**
     * 参照生成的
     */
    private Long refLmfFileId;

}
