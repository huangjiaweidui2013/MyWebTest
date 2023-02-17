package com.huang.learn.service.impl;

import com.huang.learn.domain.Cad2dXref;
import com.huang.learn.mapper.Cad2dXrefMapper;
import com.huang.learn.service.ICad2dXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文档外部参照映射Service业务层处理
 *
 * @author huang lang
 * @date 2022-06-10
 */
@Service
public class Cad2dXrefServiceImpl implements ICad2dXrefService {
    @Autowired
    private Cad2dXrefMapper cad2dXrefMapper;

    /**
     * 查询文档外部参照映射
     *
     * @param docId 文档外部参照映射主键
     * @return 文档外部参照映射
     */
    @Override
    public Cad2dXref selectCad2dXrefByDocId(Long docId) {
        return cad2dXrefMapper.selectCad2dXrefByDocId(docId);
    }

    /**
     * 查询文档外部参照映射列表
     *
     * @param cad2dXref 文档外部参照映射
     * @return 文档外部参照映射
     */
    @Override
    public List<Cad2dXref> selectCad2dXrefList(Cad2dXref cad2dXref) {
        return cad2dXrefMapper.selectCad2dXrefList(cad2dXref);
    }

    /**
     * 新增文档外部参照映射
     *
     * @param cad2dXref 文档外部参照映射
     * @return 结果
     */
    @Override
    public int insertCad2dXref(Cad2dXref cad2dXref) {
        cad2dXref.setCreateTime(new Date());
        return cad2dXrefMapper.insertCad2dXref(cad2dXref);
    }

    /**
     * 修改文档外部参照映射
     *
     * @param cad2dXref 文档外部参照映射
     * @return 结果
     */
    @Override
    public int updateCad2dXref(Cad2dXref cad2dXref) {
        return cad2dXrefMapper.updateCad2dXref(cad2dXref);
    }

    /**
     * 批量删除文档外部参照映射
     *
     * @param docIds 需要删除的文档外部参照映射主键
     * @return 结果
     */
    @Override
    public int deleteCad2dXrefByDocIds(Long[] docIds) {
        return cad2dXrefMapper.deleteCad2dXrefByDocIds(docIds);
    }

    /**
     * 删除文档外部参照映射信息
     *
     * @param docId 文档外部参照映射主键
     * @return 结果
     */
    @Override
    public int deleteCad2dXrefByDocId(Long docId) {
        return cad2dXrefMapper.deleteCad2dXrefByDocId(docId);
    }
}
