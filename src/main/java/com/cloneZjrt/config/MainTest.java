package com.cloneZjrt.config;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

    public static void main(String[] args) throws Exception{
        new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("over");
//        MyScheduler myScheduler = new MyScheduler();
//        myScheduler.quartzText();
    }


}
