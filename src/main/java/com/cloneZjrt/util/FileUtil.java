//package com.cloneZjrt.util;
//
//import com.cloneZjrt.config.InstantiationTracingBeanPostProcessor;
//import com.cloneZjrt.exception.LogicException;
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.Properties;
//
///**
// * 文件操作工具类，主要操作文件上传和
// * Created by Apple on 2018/2/11.
// */
//@Component("fileUtil")
//public class FileUtil extends PropertyPlaceholderConfigurer {
//    private static Logger logger = Logger.getLogger(FileUtil.class);
//
//    //    private static  String UPLOAD_FILE_PATH = "/Users/Apple/java/fileUpload";
//    private static FileUtil utils = null;
//    private static Properties props;
//    static {
//        try {
//            props = new Properties();
//            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("sys.properties");
//            props.load(in);
//        } catch (IOException e) {
//            logger.error("加载配置文件异常", e);
//        }
//
//    }
//
//    private FileUtil() {
//    }
//
//    public synchronized static FileUtil getInstance() {
//        if (utils == null) {
//            utils = new FileUtil();
//        }
//        return utils;
//    }
//
//    @Override
//    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
//                                     Properties props) throws BeansException {
//        super.processProperties(beanFactory, props);
//        this.props = props;
//    }
//
//    /**
//     * 写出文件
//     *
//     * @param file
//     * @param fileName
//     * @return
//     */
//    public void writeFile(MultipartFile file, String fileName) {
//        /**
//         * 检查磁盘目录，创建文件，输出文件
//         */
//        String aa = InstantiationTracingBeanPostProcessor.aa+"/"+fileName;
//        File file1 = new File(aa);
//        System.out.println(aa);
//        if (file1.exists()){
//            throw new LogicException("文件已存在");
//        }
//        try {
//            OutputStream os = new FileOutputStream(file1);
//            byte[] bytes = file.getBytes();
//            os.write(bytes);
//            os.flush();
//            os.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public String getPath(String key) {
//        return props.getProperty(key);
//    }
//
//    public void checkedDirectory(String path) {
//        File file = new File(path);
//        if (!file.isDirectory() || !file.exists()) {
//            logger.debug("make dir " + path);
//            file.mkdir();
//            logger.debug("make dir end");
//        }
//
//    }
//
//    /**
//     * 生成对应表的excel
//     * @param
//     */
//    public void cwExcel(List<WorkLogEntity> workLogEntities,String aa) {
//        try {
//            WritableWorkbook wwb = null;
//            File file = new File(aa);
//            wwb = Workbook.createWorkbook(file);
//            WritableSheet ws=wwb.createSheet("user",0);
//            Label labelId=new Label(0,0,"编号");
//            Label labelmall=new Label(1,0,"演播厅");
//            Label labelchannel=new Label(2,0,"频道");
//            Label labelprograme=new Label(3,0,"节目名称");
//            Label labelstart=new Label(4,0,"开始时间");
//            Label labelend=new Label(5,0,"结束时间");
//            Label labeluser=new Label(6,0,"联系人");
//            Label labeltel=new Label(7,0,"联系电话");
//            Label labelworkers=new Label(8,0,"员工");
//            Label labelinfo=new Label(9,0,"详情");
//            Label labeltime=new Label(10,0,"工作时间");
//            ws.addCell(labelId);
//            ws.addCell(labelmall);
//            ws.addCell(labelchannel);
//            ws.addCell(labelprograme);
//            ws.addCell(labelstart);
//            ws.addCell(labelend);
//            ws.addCell(labeluser);
//            ws.addCell(labeltel);
//            ws.addCell(labelworkers);
//            ws.addCell(labelinfo);
//            ws.addCell(labeltime);
//            for (int i = 0; i < workLogEntities.size(); i++) {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//                Date date1=sdf.parse(workLogEntities.get(i).getStarttime());
//                Date date2=sdf.parse(workLogEntities.get(i).getEndtime());
//                long time=date2.getTime()-date1.getTime();
//                time=time/1000;
//                //Label labelId_i=new Label(0,i+1,String.valueOf(workLogEntities.get(i).getUserid()));
//                Label labelId_i=new Label(0,i+1,String.valueOf(workLogEntities.get(i).getWorklogid()));
//                Label labelmall_i=new Label(1,i+1,workLogEntities.get(i).getMallname());
//                Label labelchannel_i=new Label(2,i+1,workLogEntities.get(i).getChannelname());
//                Label labelprograme_i=new Label(3,i+1,workLogEntities.get(i).getProgramename());
//                Label labelstart_i=new Label(4,i+1,workLogEntities.get(i).getStarttime());
//                Label labelend_i=new Label(5,i+1,workLogEntities.get(i).getEndtime());
//                Label labeluser_i=new Label(6,i+1,workLogEntities.get(i).getUsername());
//                Label labeltel_i=new Label(7,i+1,workLogEntities.get(i).getUsertel());
//                Label labelworkers_i=new Label(8,i+1,workLogEntities.get(i).getWorkers());
//                Label labelinfo_i=new Label(9,i+1,workLogEntities.get(i).getLoginfo());
//                Label labeltime_i=new Label(10,i+1,formatSeconds(time));
//                System.out.println(formatSeconds(time));
//                ws.addCell(labelId_i);
//                ws.addCell(labelmall_i);
//                ws.addCell(labelchannel_i);
//                ws.addCell(labelprograme_i);
//                ws.addCell(labelstart_i);
//                ws.addCell(labelend_i);
//                ws.addCell(labeluser_i);
//                ws.addCell(labeltel_i);
//                ws.addCell(labelworkers_i);
//                ws.addCell(labelinfo_i);
//                ws.addCell(labeltime_i);
//            }
//            wwb.write();
//            wwb.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 生成对应表的excel
//     * @param
//     */
////    public void cmExcel(List<MainLogEntity> mainLogEntities, String aa) {
////        try {
////            WritableWorkbook wwb = null;
////            File file = new File(aa);
////            wwb = Workbook.createWorkbook(file);
////            WritableSheet ws=wwb.createSheet("user",0);
////            Label labelId=new Label(0,0,"编号");
////            Label labeltitle=new Label(1,0,"标题");
////            Label labeluser=new Label(2,0,"维护人员");
////            Label labeltel=new Label(3,0,"电话");
////            Label labeltime=new Label(4,0,"时间");
////            Label labelmain=new Label(5,0,"主系统");
////            Label labelsub=new Label(6,0,"子系统");
////            Label labelinfo=new Label(7,0,"详情");
////            ws.addCell(labelId);
////            ws.addCell(labeltitle);
////            ws.addCell(labeluser);
////            ws.addCell(labeltel);
////            ws.addCell(labeltime);
////            ws.addCell(labelmain);
////            ws.addCell(labelsub);
////            ws.addCell(labelinfo);
////            for (int i = 0; i < mainLogEntities.size(); i++) {
////                //Label labelId_i=new Label(0,i+1,String.valueOf(workLogEntities.get(i).getUserid()));
////                Label labelId_i=new Label(0,i+1,String.valueOf(mainLogEntities.get(i).getMainlogid()));
////                Label labeltitle_i=new Label(1,i+1,mainLogEntities.get(i).getTitle());
////                Label labeluser_i=new Label(2,i+1,mainLogEntities.get(i).getUsername());
////                Label labeltel_i=new Label(3,i+1,mainLogEntities.get(i).getUsertel());
////                Label labeltime_i=new Label(4,i+1,mainLogEntities.get(i).getAddtime());
////                Label labelmain_i=new Label(5,i+1,mainLogEntities.get(i).getMainsystem());
////                Label labelsub_i=new Label(6,i+1,mainLogEntities.get(i).getSubsystem());
////                Label labelinfo_i=new Label(7,i+1,mainLogEntities.get(i).getDescription());
////                ws.addCell(labelId_i);
////                ws.addCell(labeltitle_i);
////                ws.addCell(labeluser_i);
////                ws.addCell(labeltel_i);
////                ws.addCell(labeltime_i);
////                ws.addCell(labelmain_i);
////                ws.addCell(labelsub_i);
////                ws.addCell(labelinfo_i);
////            }
////            wwb.write();
////            wwb.close();
////        }
////        catch (Exception e){
////            e.printStackTrace();
////        }
////
////    }
//
//    public static String formatSeconds(long seconds) {
//        String timeStr = seconds + "秒";
//        if (seconds > 60) {
//            long second = seconds % 60;
//            long min = seconds / 60;
//            timeStr = min/60.0 + "时";
//            if (min > 60) {
//                min = (seconds / 60) % 60;
//                long hour = (seconds / 60) / 60;
//                timeStr = hour +min/60.0+ "小时";
//            }
//        }
//        return timeStr;
//    }
//
//
//
//}
