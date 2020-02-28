//package com.cloneZjrt.config;
//
//import com.cloneZjrt.util.FileUtil;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.WebApplicationContext;
//
///**
// * spring 初始化后检查一些项目是否存在，否则需要初始化
// * Created by Apple on 2018/2/13.
// */
//@Component
//public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {
//    private Logger logger = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
//    private static  String UPLOAD_FILE_PATH = "fileUpload";
//    public static String aa = "";
//
//    @Autowired
//    WebApplicationContext wac;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        logger.info("InstantiationTracingBeanPostProcessor init....");
//        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
//            String path = FileUtil.getInstance().getPath(UPLOAD_FILE_PATH);
//            System.out.println("path:"+path);
//            String realPath = wac.getServletContext().getRealPath(path);
//            logger.info("uploadFilePath=====>>> "+realPath);
//            aa = realPath;
//            FileUtil.getInstance().checkedDirectory(realPath);
//        }
//    }
//}