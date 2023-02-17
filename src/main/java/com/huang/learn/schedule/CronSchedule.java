package com.huang.learn.schedule;

import com.huang.learn.config.MyPropertiesTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author localuser
 * create at 2022/10/14 10:56
 * @description 实现动态cron的定时任务
 */
@Slf4j
@Component
public class CronSchedule implements SchedulingConfigurer {
    @Autowired
    private MyPropertiesTest myPropertiesTest;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Runnable task = this::scheduleMethod;

        Trigger trigger = triggerContext -> {
            String cron = myPropertiesTest.getCron();
            if (!StringUtils.hasLength(cron)) {
                cron = "0 0 0/5 * * ?";
            }
            CronTrigger cronTrigger = new CronTrigger(cron);
            return cronTrigger.nextExecutionTime(triggerContext);
        };
        taskRegistrar.addTriggerTask(task, trigger);

    }

    private void scheduleMethod() {
        log.info("定时任务： " + new Date());
        System.out.println("定时任务： " + new Date());
    }


}
