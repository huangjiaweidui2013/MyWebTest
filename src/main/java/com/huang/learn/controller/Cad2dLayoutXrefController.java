package com.huang.learn.controller;

import com.huang.learn.common.AjaxResult;
import com.huang.learn.domain.Cad2dLayoutXref;
import com.huang.learn.service.ICad2dLayoutXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档外部参照lmf文件Controller
 *
 * @author huang lang
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/xref/layout")
public class Cad2dLayoutXrefController {
    @Autowired
    private ICad2dLayoutXrefService cad2dLayoutXrefService;

    /**
     * 查询文档外部参照lmf文件列表
     */
    @GetMapping("/list")
    public AjaxResult list(Cad2dLayoutXref cad2dLayoutXref) {
        List<Cad2dLayoutXref> list = cad2dLayoutXrefService.selectCad2dLayoutXrefList(cad2dLayoutXref);
        return AjaxResult.success(list);
    }

    /**
     * 获取文档外部参照lmf文件详细信息
     */
    @GetMapping(value = "/{docId}")
    public AjaxResult getInfo(@PathVariable("docId") Long docId) {
        return AjaxResult.success(cad2dLayoutXrefService.selectCad2dLayoutXrefByDocId(docId));
    }

    /**
     * 新增文档外部参照lmf文件
     */
    @PostMapping
    public AjaxResult add(@RequestBody Cad2dLayoutXref cad2dLayoutXref) {
        return AjaxResult.success(cad2dLayoutXrefService.insertCad2dLayoutXref(cad2dLayoutXref));
    }

    /**
     * 修改文档外部参照lmf文件
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Cad2dLayoutXref cad2dLayoutXref) {
        return AjaxResult.success(cad2dLayoutXrefService.updateCad2dLayoutXref(cad2dLayoutXref));
    }

    /**
     * 删除文档外部参照lmf文件
     */
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds) {
        return AjaxResult.success(cad2dLayoutXrefService.deleteCad2dLayoutXrefByDocIds(docIds));
    }
}
