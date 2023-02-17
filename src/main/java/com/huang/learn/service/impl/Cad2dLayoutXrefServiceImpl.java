package com.huang.learn.service.impl;

import com.huang.learn.domain.Cad2dLayoutXref;
import com.huang.learn.mapper.Cad2dLayoutXrefMapper;
import com.huang.learn.service.ICad2dLayoutXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文档外部参照lmf文件Service业务层处理
 *
 * @author huang lang
 * @date 2022-06-10
 */
@Service
public class Cad2dLayoutXrefServiceImpl implements ICad2dLayoutXrefService {
    @Autowired
    private Cad2dLayoutXrefMapper cad2dLayoutXrefMapper;

    /**
     * 查询文档外部参照lmf文件
     *
     * @param docId 文档外部参照lmf文件主键
     * @return 文档外部参照lmf文件
     */
    @Override
    public Cad2dLayoutXref selectCad2dLayoutXrefByDocId(Long docId) {
        return cad2dLayoutXrefMapper.selectCad2dLayoutXrefByDocId(docId);
    }

    /**
     * 查询文档外部参照lmf文件列表
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 文档外部参照lmf文件
     */
    @Override
    public List<Cad2dLayoutXref> selectCad2dLayoutXrefList(Cad2dLayoutXref cad2dLayoutXref) {
        return cad2dLayoutXrefMapper.selectCad2dLayoutXrefList(cad2dLayoutXref);
    }

    /**
     * 新增文档外部参照lmf文件
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 结果
     */
    @Override
    public int insertCad2dLayoutXref(Cad2dLayoutXref cad2dLayoutXref) {
        cad2dLayoutXref.setCreateTime(new Date());
        return cad2dLayoutXrefMapper.insertCad2dLayoutXref(cad2dLayoutXref);
    }

    /**
     * 修改文档外部参照lmf文件
     *
     * @param cad2dLayoutXref 文档外部参照lmf文件
     * @return 结果
     */
    @Override
    public int updateCad2dLayoutXref(Cad2dLayoutXref cad2dLayoutXref) {
        cad2dLayoutXref.setUpdateTime(new Date());
        return cad2dLayoutXrefMapper.updateCad2dLayoutXref(cad2dLayoutXref);
    }

    /**
     * 批量删除文档外部参照lmf文件
     *
     * @param docIds 需要删除的文档外部参照lmf文件主键
     * @return 结果
     */
    @Override
    public int deleteCad2dLayoutXrefByDocIds(Long[] docIds) {
        return cad2dLayoutXrefMapper.deleteCad2dLayoutXrefByDocIds(docIds);
    }

    /**
     * 删除文档外部参照lmf文件信息
     *
     * @param docId 文档外部参照lmf文件主键
     * @return 结果
     */
    @Override
    public int deleteCad2dLayoutXrefByDocId(Long docId) {
        return cad2dLayoutXrefMapper.deleteCad2dLayoutXrefByDocId(docId);
    }
}
