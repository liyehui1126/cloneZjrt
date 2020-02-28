package com.cloneZjrt.controller;

import com.cloneZjrt.exception.LogicException;
import com.cloneZjrt.model.UserEntity;
import com.cloneZjrt.service.UserService;
import jxl.Workbook;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Apple on 2020/2/12.
 */
@Controller
@RequestMapping("/file")
@ResponseBody
public class FileUploadController {

    @Resource
    private UserService userService;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    //https://www.cnblogs.com/hamawep789/p/10923722.html，
    //springmvc文件上传操作
    /**
     * 文件上传方法，多个文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public String updateItems(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        /**
         * 把图片保存到图片目录下,保存图片，避免文件名可能会重复，所以为每个文件生成一个新的文件名
         */
        try{
            String uuid = UUID.randomUUID().toString();
            // 截取文件的扩展名(如.jpg)
            String oriName = file.getOriginalFilename();
            String extName = oriName.substring(oriName.lastIndexOf("."));
            String fileName = uuid+extName;

            /**
             * 这里的aa可以获得当前工程运行目录，并建立一个upload文件夹
             * 这样的缺点是，重新部署（即版本更新）会造成文件丢失
             * 所以可以根据自己的业务需求，将aa改成固定的文件服务器地址
             */
            String aa = request.getServletContext().getRealPath("/upload/") + fileName;
            File filePath = new File(aa);
            System.out.println(aa);
            if (filePath.exists()){
                throw new LogicException("文件已存在");
            }
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
                System.out.println("创建目录" + filePath);
            }
            try {
                OutputStream os = new FileOutputStream(filePath);
                byte[] bytes = file.getBytes();
                os.write(bytes);
                os.flush();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            return "error";
        }
        return "success";
    }

    @RequestMapping(value = "/getExcel", method = {RequestMethod.GET})
    public String cwExcel(HttpServletRequest request) {

        String aa = request.getServletContext().getRealPath("/upload/") + UUID.randomUUID().toString() + ".xls";

        try {
            WritableWorkbook wwb = null;
            File filePath = new File(aa);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
                System.out.println("创建目录" + filePath);
            }
            wwb = Workbook.createWorkbook(filePath);
            WritableSheet ws=wwb.createSheet("user",0);
            Label userId=new Label(0,0,"ID");
            Label userName=new Label(1,0,"姓名");
            ws.addCell(userId);
            ws.addCell(userName);
            List<UserEntity> userEntities = userService.findAll();
            for (int i = 0; i < userEntities.size(); i++) {
                //Label labelId_i=new Label(0,i+1,String.valueOf(workLogEntities.get(i).getUserid()));
                Label labelId_i=new Label(0,i+1,String.valueOf(userEntities.get(i).getUserId()));
                Label labelmall_i=new Label(1,i+1,userEntities.get(i).getUserName());
                ws.addCell(labelId_i);
                ws.addCell(labelmall_i);
            }
            wwb.write();
            wwb.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return aa;
    }

}
