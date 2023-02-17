package com.huang.demo;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author localuser
 * create at 2023/2/7 10:11
 * @description
 */
@Slf4j
public class SimpleTest {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void convertDate() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);
        log.info("new date: {}", format.format(from));
        Date jdkDate = DateUtil.offsetDay(from, 90).toJdkDate();
        log.info("jdkDate: {}", format.format(jdkDate));

    }
}
