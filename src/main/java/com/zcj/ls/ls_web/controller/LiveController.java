package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.dao.NewsRepository;
import com.zcj.ls.ls_web.entity.News;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页路由
 * 后缀带“B”的，为后台管理页面跳转。
 */
@Controller
public class LiveController {

    //存储查询结果说明：成功或者失败的原因
    private final NewsRepository newsRepository;
    private String resultMessage = "";

    public LiveController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    //前台显示
    @RequestMapping({"/live","/equipment/camera/camera.do"})     //映射多个路径
    public String index(Model model) {

        return "front/live";
    }

}
