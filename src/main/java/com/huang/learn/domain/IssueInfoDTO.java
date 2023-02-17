package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

/**
 * @author localuser
 * create at 2023/2/14 16:50
 * @description 问题基本信息DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueInfoDTO {
    private String issueId;//全局唯一id，区分每个问题
    private Long docId;//文档id
    private BigInteger handleId;//句柄id，对应图纸的某个视图
    private Integer userId;//产生问题时的用户id
    private String source;//问题汇报来源，APP_WEB(电脑端浏览器),APP_MOBILE(移动端浏览器),SERVER_CSHARP(C#服务端),SERVER_CPLUSPLUS(C++服务端)
    private String name;//问题名称
    private String time;//(yyyy-MM-dd HH:mm:ss),问题产生时的日期时间
    private String ip;//产生问题的服务器ip地址
    private List<String> filePaths;//问题相关文件（日志文件、dump文件等）的服务器存放路径的列表
    private String remark;//备注，存放与问题相关的其它关键信息
}
