package com.huang.learn.controller;

import com.huang.learn.common.AjaxResult;
import com.huang.learn.domain.Cad2dXref;
import com.huang.learn.service.ICad2dXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档外部参照映射Controller
 *
 * @author huang lang
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/xref")
public class Cad2dXrefController {
    @Autowired
    private ICad2dXrefService cad2dXrefService;

    /**
     * 查询文档外部参照映射列表
     */
    @GetMapping("/list")
    public AjaxResult list(Cad2dXref cad2dXref) {
        List<Cad2dXref> list = cad2dXrefService.selectCad2dXrefList(cad2dXref);
        return AjaxResult.success(list);
    }

    /**
     * 获取文档外部参照映射详细信息
     */
    @GetMapping(value = "/{docId}")
    public AjaxResult getInfo(@PathVariable("docId") Long docId) {
        return AjaxResult.success(cad2dXrefService.selectCad2dXrefByDocId(docId));
    }

    /**
     * 新增文档外部参照映射
     */
    @PostMapping
    public AjaxResult add(@RequestBody Cad2dXref cad2dXref) {
        return AjaxResult.success(cad2dXrefService.insertCad2dXref(cad2dXref));
    }

    /**
     * 修改文档外部参照映射
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Cad2dXref cad2dXref) {
        return AjaxResult.success(cad2dXrefService.updateCad2dXref(cad2dXref));
    }

    /**
     * 删除文档外部参照映射
     */
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds) {
        return AjaxResult.success(cad2dXrefService.deleteCad2dXrefByDocIds(docIds));
    }
}
