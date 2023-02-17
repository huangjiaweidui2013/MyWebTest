package com.huang.learn.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author localuser
 * create at 2023/1/12 14:16
 * @description 关联查询
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConjunctiveQueryTest {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 关联查询，相当于 select * from blog b left join article a on b.createId = a.userId where b.createId = 4
     */
    @Test
    public void testQuery() {
        //主表条件
        // 1 在LookupOperation前的操作，针对主表操作
        Criteria criteria = Criteria.where("createId").gte(4);

        //联表
        //2 LookupOperation，指定关联doc，关联字段，和联表结果集别名。并将结果集以数组形式嵌入到**<1>**的对应结果中。
        //此时整个结果集只会剩下存在关联关系的数据（left join）。
        // 关联oid 参数1-子集合的名称,参数2-父表id,参数3-字表关联父表的外键,参数4-查询子结合后给的别名as
        LookupOperation lookupOperation = LookupOperation
                .newLookup()
                //次表
                .from("article")
                //主表关联字段
                .localField("createId")
                //次表关联字段
                .foreignField("userId")
                //临时集合别名，次表的结果会以数组的形式存储在这个字段中
                .as("art");

        MatchOperation match1 = Aggregation.match(criteria);

        //子表条件，注意别名与上面 lookup 保持一致
        MatchOperation match2 = Aggregation.match(Criteria.where("art.thumbUp").gte(1));

        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Order.desc("art.createTime")));

        GroupOperation groupOperation = Aggregation.group("blog.createId")
                .first("art.title").as("art_title")
                .first("art.content").as("art_content")
                .first("art.createTime").as("art_createTime");

        ProjectionOperation project = Aggregation.project("art_title", "art_content", "art_createTime");


//        Aggregation aggregation = Aggregation.newAggregation(match1, lookupOperation, sortOperation);
        Aggregation aggregation = Aggregation.newAggregation(match1, lookupOperation, sortOperation, groupOperation, project);

        // blog 是主表
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "blog", Map.class);
        log.info("[results] = {}", results.getUniqueMappedResult());

    }


}
