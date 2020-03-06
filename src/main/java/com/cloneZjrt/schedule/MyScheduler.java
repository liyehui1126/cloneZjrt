package com.cloneZjrt.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@EnableScheduling       //第一种方式
@Component
public class MyScheduler {

    public void quartzText() throws Exception{
        // 1、创建调度器Scheduler
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).withIdentity("job1", "group1").build();

        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1").startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever()).build();//一直执行

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }

//    @Scheduled(cron = "${schedule.task.test}")
//    public void doSomething() {
//        System.out.println("do something");
//    }


    @Scheduled(cron="${schedule.task.doSomething}")   //第一种方式
    public void doSomething() {

        System.out.println("do something:" + new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date()));
    }

//    @Scheduled(cron="${schedule.task.doOtherThing}") //每5秒执行一次
//    public void doOtherThing() {
//        System.out.println("do Otherthing:" + new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date()));
//    }
}
