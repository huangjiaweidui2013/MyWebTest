package com.huang.learn.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huang.learn.common.AjaxResult;
import com.huang.learn.domain.Cad2dFont;
import com.huang.learn.service.ICad2dFontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文档字体Controller
 *
 * @author huang lang
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/font")
public class Cad2dFontController {
    @Autowired
    private ICad2dFontService cad2dFontService;

    /**
     * 查询文档外部参照映射列表
     */
    @GetMapping("/list")
    public AjaxResult list(Cad2dFont cad2dFont) {
        LambdaQueryWrapper<Cad2dFont> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(cad2dFont.getDocId() != null, Cad2dFont::getDocId, cad2dFont.getDocId());
        List<Cad2dFont> list = cad2dFontService.list(lambdaQueryWrapper);
        return AjaxResult.success(list);
    }

    /**
     * 获取文档外部参照映射详细信息
     */
    @GetMapping(value = "/{docId}")
    public AjaxResult getInfo(@PathVariable("docId") Long docId) {
        return AjaxResult.success(cad2dFontService.getById(docId));
    }

    /**
     * 新增文档外部参照映射
     */
    @PostMapping
    public AjaxResult add(@RequestBody Cad2dFont cad2dFont) {
        Cad2dFont entity = Cad2dFont.builder()
                .docId(IdUtil.getSnowflake().nextId())
                .fonts(cad2dFont.getFonts())
                .createTime(new Date())
                .createBy(0)
                .build();
        return AjaxResult.success(cad2dFontService.save(entity));
    }

    /**
     * 修改文档外部参照映射
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Cad2dFont cad2dFont) {
        cad2dFont.setUpdateTime(new Date());
        cad2dFont.setUpdateBy(0);
        return AjaxResult.success(cad2dFontService.updateById(cad2dFont));
    }

    /**
     * 删除文档外部参照映射
     */
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds) {
        return AjaxResult.success(cad2dFontService.removeByIds(Arrays.asList(docIds)));
    }
}
