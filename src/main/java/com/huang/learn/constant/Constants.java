package com.huang.learn.constant;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author localuser
 * create at 2022/11/10 14:49
 * @description 常量类
 */
public class Constants {

    public static final long DATA_CENTER_ID = IdUtil.getDataCenterId(31L);

    public static final long WORKER_ID = IdUtil.getWorkerId(DATA_CENTER_ID, 31L);

    public static final Snowflake SNOW_FLAKE = IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID);
}
