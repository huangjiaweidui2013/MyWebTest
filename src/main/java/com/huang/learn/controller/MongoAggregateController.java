package com.huang.learn.controller;

/**
 * @author localuser
 * create at 2023/1/9 16:23
 * @description
 */

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huang.learn.common.AjaxResult;
import com.huang.learn.domain.Article;
import com.huang.learn.domain.Cad2dFont;
import com.huang.learn.repository.ArticleRepository;
import com.huang.learn.service.ICad2dFontService;
import com.huang.learn.util.MongoBusinessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 文档字体Controller
 *
 * @author huang lang
 * @date 2022-06-10
 */
@RestController
@RequestMapping("/mongo")
public class MongoAggregateController {
    @Autowired
    private ICad2dFontService cad2dFontService;

    @Autowired
    private ArticleRepository articleRepository;

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
     * 同时操作Mysql 和 Mongodb 数据库数据
     */
    @PostMapping
    public AjaxResult add(@RequestBody Cad2dFont cad2dFont) {
        Map<String, Object> result = new HashMap<>();
        long nextId = IdUtil.getSnowflake().nextId();
        Cad2dFont entity = Cad2dFont.builder()
                .docId(nextId)
                .fonts(cad2dFont.getFonts())
                .createTime(new Date())
                .createBy(0)
                .build();
        cad2dFontService.save(entity);
        Cad2dFont font = cad2dFontService.getById(nextId);
        result.put("font", font);
        Article article = MongoBusinessUtil.generateArticle(nextId);
        articleRepository.save(article);
        Optional<Article> articleOptional = articleRepository.findById(nextId);
        articleOptional.ifPresent(article1 -> {
            result.put("article", articleOptional.get());
        });
        return AjaxResult.success(result);
    }
}
