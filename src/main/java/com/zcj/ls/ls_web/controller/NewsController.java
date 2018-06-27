package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 关于我们页面路由
 */
@Controller
public class NewsController {

    //新闻列表  前台显示
    @RequestMapping("/news")
    public String news(){

        return "front/news";
    }

    //新闻详情  前台展示
    @RequestMapping("/news_d")
    public String newsDetail(){

        return "front/news_d";
    }

    //新闻列表  后台显示
    @RequestMapping("/newsShow")
    public String newsShow(){
        return "back/newsShow";
    }

    //新闻详情  后台编辑
    @RequestMapping("/news_edit")
    public String newsEdit(){

        return "newsEdit";
    }


}
