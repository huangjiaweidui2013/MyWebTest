package com.huang.learn.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.huang.learn.domain.Blog;
import com.huang.learn.util.MongoBusinessUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        Blog blog = MongoBusinessUtil.generateBlog(1L);
        blogRepository.save(blog);

        //根据id属性进行新增或更新
//        mongoTemplate.save(blog);
        log.info("[blog] = {} ", JSONUtil.toJsonStr(blog));
    }

    /**
     * 测试批量新增列表
     */
    @Test
    public void testSaveList() {
        List<Blog> list = CollUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(MongoBusinessUtil.generateBlog(null));
        }
        blogRepository.saveAll(list);
        log.info("[blog ids] = {}", JSONUtil.toJsonStr(list.stream()
                .map(Blog::getId).collect(Collectors.toList())));
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        Long id = 1L;
        blogRepository.findById(id).ifPresent(blog -> {
            blog.getArticle().setTitle("更新之后的标题: " + blog.getArticle().getTitle());
            blog.setUpdateTime(DateUtil.date());
            blogRepository.save(blog);
        });
        blogRepository.findById(id).ifPresent(blog -> {
            log.info("[blog] = {} ", JSONUtil.toJsonStr(blog));
        });
    }

    /**
     * 使用 MongoTemplate 进行更新
     */
    @Test
    public void testUpdate2() {
        Long id = 1L;
        blogRepository.findById(id).ifPresent(blog -> {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            Update update = new Update();
            update.set("article.content", "更新之后的文章内容：" + blog.getArticle().getContent());
            mongoTemplate.updateFirst(query, update, "blog");
            log.info("[blog] = {} ", JSONUtil.toJsonStr(blog));
        });
    }
}