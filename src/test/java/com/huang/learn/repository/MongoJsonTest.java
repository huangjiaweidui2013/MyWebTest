package com.huang.learn.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author localuser
 * create at 2023/1/10 16:45
 * @description 直接操作JSON数据
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoJsonTest {

    private static final String COLLECTION_NAME = "json_test";

    private static final String ID_STRING = "1610435429345845250";
    public static final String COMPLEX_JSON = "complex_json";

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 测试新增
     */
    @Test
    public void testSave() {
        String json = "{\"_id\": \"1610435429345845250\", " +
                "\"ownerId\": 2124, \"pathMap\": [], \"fromFile\": false, " +
                "\"sourcePath\": \"/data/upload/20230104/1610435420542001152/dwgs/1610435429345845248.dwg\", " +
                "\"showFontList\": true, " +
                "\"thumbnailPath\": \"/data/temp/20230104/1610435420542001152/1610435429345845248/1610435429345845248\"," +
                " \"graphicDataPath\": \"/data/temp/20230104/1610435420542001152/1610435429345845248/layout_default.lmf\"," +
                " \"projectFontList\": true, \"alternateFontList\": true}";
        mongoTemplate.save(json, COLLECTION_NAME);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(ID_STRING));
        List<String> list = mongoTemplate.find(query, String.class, COLLECTION_NAME);
        if (!CollectionUtils.isEmpty(list)) {
            log.info("[json] = {}", list.get(0));
        }
    }

    /**
     * 从 json 文件中读取内容并写入 Mongodb
     */
    @Test
    public void testSaveFromJsonFile() {
        try {
            byte[] bytes;
            ClassPathResource resource = new ClassPathResource("files/complex_json.json");
            InputStream in = resource.getInputStream();
            bytes = new byte[in.available()];
            in.read(bytes);
            String json = new String(bytes);
            mongoTemplate.save(json, COMPLEX_JSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testQuery() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(ID_STRING));
        String target = mongoTemplate.findOne(query, String.class, COLLECTION_NAME);
        if (StringUtils.isNoneBlank(target)) {
            log.info("[json] = {}", target);
        }
    }

    @Test
    public void testUpdate() {
        Map<String, String> itemMap = new HashMap<>();
        itemMap.put("srcFont", RandomUtil.randomString(6));
        itemMap.put("tarFont", RandomUtil.randomString(8));

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(ID_STRING));

        Update update = new Update();
        //把value追加到field里面去，field一定要是数组类型才行，如果field不存在，会新增一个数组类型加进去
//        update.push("pathMap", itemMap);

        //Spring-data-mongodb关于多层嵌套数组的操作,可以通过$[]设置多个占位符修改
        //注意：$[]只在mongodb3.6版本及以上才能使用，否则会报错filterArray指令不存在；
        update.filterArray(Criteria.where("ele.tarFont").is("lldjwyra"));
        update.set("pathMap.$[ele].srcFont", RandomUtil.randomString(6) + ".shx");

        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        String target = mongoTemplate.findOne(query, String.class, COLLECTION_NAME);
        if (StringUtils.isNoneBlank(target)) {
            log.info("[json] = {}", target);
        }
    }

    /**
     * 多层级数组的属性修改
     */
    @Test
    public void testUpdateComplexJson() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
//        update.set("data.rows.$[].content", DateUtil.date().toString());
//        mongoTemplate.updateMulti(query, update, COMPLEX_JSON);

        //多层级数组的属性修改
        update.set("data.rows.$[m].pathMap.$[n].srcFont.$[p].historyName", "修改时间： " + DateUtil.date().toString() + ".shx");
        update.filterArray(Criteria.where("m.id").is(7407));
        update.filterArray(Criteria.where("n.id").is("101"));
        update.filterArray(Criteria.where("p.id").is(2001));
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, COMPLEX_JSON);

        log.info("[query] = {}, [updateResult] = {}", query, updateResult);
    }
}
