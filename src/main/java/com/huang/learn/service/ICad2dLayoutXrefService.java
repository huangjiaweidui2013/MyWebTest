package com.huang.learn.service;

import com.huang.learn.domain.Cad2dLayoutXref;

import java.util.List;

/**
 * 文档外部参照lmf文件Service接口
 *
 * @author huang lang
 * @date 2022-06-10
 */
public interface ICad2dLayoutXrefService {
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
     * 批量删除文档外部参照lmf文件
     *
     * @param docIds 需要删除的文档外部参照lmf文件主键集合
     * @return 结果
     */
    public int deleteCad2dLayoutXrefByDocIds(Long[] docIds);

    /**
     * 删除文档外部参照lmf文件信息
     *
     * @param docId 文档外部参照lmf文件主键
     * @return 结果
     */
    public int deleteCad2dLayoutXrefByDocId(Long docId);
}
