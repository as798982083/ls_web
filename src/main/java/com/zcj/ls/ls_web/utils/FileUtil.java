package com.zcj.ls.ls_web.utils;

import com.zcj.ls.ls_web.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
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

    @Autowired
    public static WebConfig webConfig;
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
        String fileName = df.format(calendar.getTime())+file.getOriginalFilename();
        System.out.println("fileName："+fileName);

        //MultipartFile的方法直接写文件
        try {
            //保存文件的绝对路径
            //获取服务器的绝对路径
            String path = "D:\\ls_web\\upload";
            File folder = new File(path);
            //获取项目路径
//            String path = ResourceUtils.getURL("src\\main\\resources\\static\\upload").getPath();
//            File folder = new File(path.substring(1, path.length()));
            //创建目录和文件
            if(!folder.exists()){
                folder.mkdirs();
            }
            File file1 = new File(folder.getPath()+"\\"+fileName);
            //写到文件中并保存相对路径
            file.transferTo(file1);
            url = fileName;

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
