package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author localuser
 * create at 2023/1/10 11:08
 * @description 技能对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    private Level level;

    private String title;

    public static enum Level {
        LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5
    }


}
