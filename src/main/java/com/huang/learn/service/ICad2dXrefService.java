package com.huang.learn.service;

import com.huang.learn.domain.Cad2dXref;

import java.util.List;

/**
 * 文档外部参照映射Service接口
 *
 * @author huang lang
 * @date 2022-06-10
 */
public interface ICad2dXrefService {
    /**
     * 查询文档外部参照映射
     *
     * @param docId 文档外部参照映射主键
     * @return 文档外部参照映射
     */
    public Cad2dXref selectCad2dXrefByDocId(Long docId);

    /**
     * 查询文档外部参照映射列表
     *
     * @param cad2dXref 文档外部参照映射
     * @return 文档外部参照映射集合
     */
    public List<Cad2dXref> selectCad2dXrefList(Cad2dXref cad2dXref);

    /**
     * 新增文档外部参照映射
     *
     * @param cad2dXref 文档外部参照映射
     * @return 结果
     */
    public int insertCad2dXref(Cad2dXref cad2dXref);

    /**
     * 修改文档外部参照映射
     *
     * @param cad2dXref 文档外部参照映射
     * @return 结果
     */
    public int updateCad2dXref(Cad2dXref cad2dXref);

    /**
     * 批量删除文档外部参照映射
     *
     * @param docIds 需要删除的文档外部参照映射主键集合
     * @return 结果
     */
    public int deleteCad2dXrefByDocIds(Long[] docIds);

    /**
     * 删除文档外部参照映射信息
     *
     * @param docId 文档外部参照映射主键
     * @return 结果
     */
    public int deleteCad2dXrefByDocId(Long docId);
}
