package com.cloneZjrt.schedule;

import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Data
public class MyQuartzJob extends QuartzJobBean {

    private int timeout;//间隔多长时间调度开始
    private String stringValue;
    private static int i = 0;

    //需要调度的任务
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("QuartzJobBean-调度" + ++i + "进行中...");
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("MyQuartzJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
    }

}