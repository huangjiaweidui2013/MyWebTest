package com.huang.learn.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.huang.learn.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author localuser
 * create at 2023/1/9 16:28
 * @description
 */
public class MongoBusinessUtil {
    private static Snowflake snowflake = IdUtil.getSnowflake();

    public static Article generateArticle(Long id) {
        return Article.builder()
                .id(id == null ? snowflake.nextId() : id)
                .userId(RandomUtil.randomLong(100))
                .title(RandomUtil.randomString(20))
                .content("文章内容：" + RandomUtil.randomString(50))
                .createTime(DateUtil.date())
                .thumbUp(RandomUtil.randomLong(100))
                .visits(RandomUtil.randomLong(1000))
                .build();
    }

    public static Blog generateBlog(Long id) {
        return Blog.builder()
                .id(id == null ? snowflake.nextId() : id)
                .createId(RandomUtil.randomLong(100))
                .createTime(DateUtil.date())
                .article(generateArticle(id))
                .build();
    }

    public static Dept generateDept(Long id) {
        return Dept.builder()
                .id(id == null ? snowflake.nextId() : id)
                .title("title=" + RandomUtil.randomString(20))
                .loc(RandomUtil.randomString("湖北省武汉市洪山区关山街道", 3))
                .build();
    }

    public static Staff generateStaff(Long id) {
        Dept dept = generateDept(id);
        int i = RandomUtil.randomInt(1, 5);
        List<Skill> skills = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            skills.add(Skill.builder().title(RandomUtil.randomString(8)).level(Skill.Level.values()[j]).build());
        }
        return Staff.builder()
                .id(id == null ? snowflake.nextId() : id)
                .name("name=" + RandomUtil.randomString(20))
                .dept(dept)
                .skills(skills)
                .phone("181" + RandomUtil.randomInt(10000000, 99999999))
                .build();
    }
}
