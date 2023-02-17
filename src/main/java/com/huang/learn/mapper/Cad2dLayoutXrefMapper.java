package com.huang.learn.mapper;

import com.huang.learn.domain.Cad2dLayoutXref;

import java.util.List;

/**
 * 文档外部参照lmf文件Mapper接口
 *
 * @author huang lang
 * @date 2022-06-10
 */
public interface Cad2dLayoutXrefMapper {
    /**
     * 查询文档外部参照lmf文件
     *
     * @param docId 文档外部参照lmf文件主键
     * @return 文档外部参照lmf文件
     */
    public Cad2dLayoutXref selectCad2dLayoutXrefByDocId(Long docId);

    /**
     * 查询文档外部参照lmf文件列表
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 文档外部参照lmf文件集合
     */
    public List<Cad2dLayoutXref> selectCad2dLayoutXrefList(Cad2dLayoutXref cad2dLayoutXref);

    /**
     * 新增文档外部参照lmf文件
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 结果
     */
    public int insertCad2dLayoutXref(Cad2dLayoutXref cad2dLayoutXref);

    /**
     * 修改文档外部参照lmf文件
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 结果
     */
    public int updateCad2dLayoutXref(Cad2dLayoutXref cad2dLayoutXref);

    /**
     * 删除文档外部参照lmf文件
     *
     * @param docId 文档外部参照lmf文件主键
     * @return 结果
     */
    public int deleteCad2dLayoutXrefByDocId(Long docId);

    /**
     * 批量删除文档外部参照lmf文件
     *
     * @param docIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCad2dLayoutXrefByDocIds(Long[] docIds);
}
