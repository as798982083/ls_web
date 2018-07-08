package com.zcj.ls.ls_web.utils;

import com.zcj.ls.ls_web.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//3个关键，必须都存在，才能将webConfig成功注入进来。
@Component  //关键1
public class FileUtil {

    private static FileUtil fileUtil;   //关键2

    @Autowired
    public WebConfig webConfig;

    // 关键3
    @PostConstruct
    public void init() {
        fileUtil = this;
        fileUtil.webConfig = this.webConfig;
    }

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
            String path = fileUtil.webConfig.getUploadPath();
            File folder = new File(path);
            //创建目录和文件
            if(!folder.exists()){
                folder.mkdirs();
            }
            File file1 = new File(folder.getPath()+"\\"+fileName);
            //写到文件中并保存相对路径，由于在yml中配置了静态资源路径，
            // 路径下的数据可以直接访问，因此只需要保存文件名即可。
            file.transferTo(file1);
            url = fileName;

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}
