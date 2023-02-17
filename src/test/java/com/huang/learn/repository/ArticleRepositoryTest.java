package com.huang.learn.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.huang.learn.domain.Article;
import com.huang.learn.util.MongoBusinessUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        Article article = MongoBusinessUtil.generateArticle(1L);
        articleRepo.save(article);

        //根据id属性进行新增或更新
        mongoTemplate.save(article);
        log.info("[article] = {} ", JSONUtil.toJsonStr(article));
    }

    /**
     * 测试批量新增列表
     */
    @Test
    public void testSaveList() {
        List<Article> list = CollUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(MongoBusinessUtil.generateArticle(null));
        }
        articleRepo.saveAll(list);
        log.info("[article ids] = {}", JSONUtil.toJsonStr(list.stream()
                .map(Article::getId).collect(Collectors.toList())));
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        Long id = 1L;
        articleRepo.findById(id).ifPresent(article -> {
            article.setTitle("更新之后的标题: " + article.getTitle());
            article.setUpdateTime(DateUtil.date());
            articleRepo.save(article);
        });
        articleRepo.findById(id).ifPresent(article -> {
            log.info("[article] = {} ", JSONUtil.toJsonStr(article));
        });
    }

    /**
     * 测试点赞数、访客数，使用更优雅/高效的方式更新点赞、访客
     */
    @Test
    public void testThumbUp2() {
        Long id = 1L;
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbUp", 1L);
        update.inc("visits", 1L);
        mongoTemplate.updateFirst(query, update, "article");
        articleRepo.findById(id).ifPresent(article -> {
            log.info("[article] = {} ", JSONUtil.toJsonStr(article));
        });
    }


    /**
     * 测试删除
     */
//    @Test
//    public void testDelete() {
//        Long id = 1612285176491540480L;
//        // 根据主键删除
//        articleRepo.deleteById(id);
//
//        // 全部删除
//        articleRepo.deleteAll();
//    }

    /**
     * 测试分页排序查询
     */
    @Test
    public void testQuery() {
        Sort sort = Sort.by("thumbUp", "createTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Article> all = articleRepo.findAll(pageRequest);
        log.info("[总页数] = {}, [总条数] = {}, [当前页数据] = {}", all.getTotalPages(), all.getTotalElements(),
                JSONUtil.toJsonStr(all.getContent().stream()
                        .map(article -> "文章标题：" + article.getTitle() + ", 点赞数：" +
                                article.getThumbUp() + ", 更新时间：" +
                                article.getUpdateTime()).collect(Collectors.toList())));
    }

    /**
     * 查询,条件匹配/排序/分页, 基于继承 MongoRepository 实现
     */
    @Test
    public void testQuery2() {
        //匹配条件构造
        Article article = Article.builder()
                .title("drx3f2lyvjgms8keqc9l")
                .content("文章内容：qkjobvn521q0k58v4jr3g702ua560tbmdbv89yjo9nxgmw7gpb")
                .build();

        //指定字段匹配类型
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                //忽略大小写
                .withIgnoreCase()
                //指定 title 字段为精准匹配
                .withMatcher("title", ExampleMatcher.GenericPropertyMatcher::exact)
                //指定 content 字段为模糊匹配
                .withMatcher("content", ExampleMatcher.GenericPropertyMatcher::contains);

        Example<Article> example = Example.of(article, exampleMatcher);

        //排序规则
        Sort sort = Sort.by("createTime").descending();

        //分页设置
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Article> all = articleRepo.findAll(example, pageRequest);
        log.info("[复杂查询的结果] = {}", JSONUtil.toJsonStr(all.getContent()));
    }

    /**
     * 查询,条件匹配/排序/分页, 基于继承 MongoTemplate 实现
     */
    @Test
    public void testQuery3() {
        Long id = 1L;
        //查询条件
        Criteria criteria = Criteria
                //精确匹配
                .where("title").is("drx3f2lyvjgms8keqc9l")
                //模糊匹配，使用正则: .*[xxx].*
                .and("content").regex(".*文章内容：qkjobvn521q0k58v4jr3g702ua560tbmdbv89yjo9nxgmw7gpb.*")
                //匹配明细里的字段
                .and("ids").elemMatch(Criteria.where("id").gte(id))
                //匹配多个并行或
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("visits").exists(false),
                                Criteria.where("visits").gt(1)
                        ),
                        new Criteria().orOperator(
                                Criteria.where("thumbUp").exists(false),
                                Criteria.where("thumbUp").gt(1)
                        )
                );

        //排序规则
        Sort sort = Sort.by("createTime").descending();

        //分页
        PageRequest pageRequest = PageRequest.of(0, 5, sort);

        Query query = Query.query(criteria).with(sort).with(pageRequest);

        List<Article> articles = mongoTemplate.find(query, Article.class);
        PageImpl<Article> page = (PageImpl<Article>)
                PageableExecutionUtils.getPage(articles, pageRequest,
                        () -> mongoTemplate.count(Query.query(criteria), Article.class));

        Optional.of(page.getContent()).ifPresent(articles1 -> {
            articles1.forEach(article -> {
                log.info("[article] = {} ", JSONUtil.toJsonStr(article));
            });
        });

    }


}