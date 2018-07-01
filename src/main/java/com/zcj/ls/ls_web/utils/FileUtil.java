package com.zcj.ls.ls_web.utils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileUtil {

    /**
     * 保存文件，并返回文件的相对路径。
     * @param file 临时文件名称
     * @return 文件相对路径
     */
    public static String saveFile(MultipartFile file, HttpServletRequest request) {
        String url = "";
        //保存时的文件名
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String dateName = df.format(calendar.getTime())+file.getOriginalFilename();

        System.out.println(dateName);
        //保存文件的绝对路径
        WebApplicationContext webApplicationContext = (WebApplicationContext) SpringUtil.getApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String realPath = servletContext.getRealPath("/");
        String filePath = realPath + File.separator+dateName;
        System.out.println("绝对路径:"+filePath);

        File newFile = new File(filePath);

        //MultipartFile的方法直接写文件
        try {

            //上传文件
            file.transferTo(newFile);

            //数据库存储的相对路径
            String projectPath = servletContext.getContextPath();
            String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;
            url = contextpath + "/resource/"+dateName;
            System.out.println("相对路径:"+url);
            //文件名与文件URL存入数据库表

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
