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
public class IndexController {

    //存储查询结果说明：成功或者失败的原因
    private final NewsRepository newsRepository;
    private String resultMessage = "";

    public IndexController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    //前台显示
    @RequestMapping({"/","/index","/login"})     //映射多个路径
    public String index(Model model) {
        List<News> newsList = newsList();
        model.addAttribute("newsList", newsList);
        model.addAttribute("page", "index");
        return "front/index";
    }

    public List<News> newsList(){
        //创建查询条件数据对象:只查找已发布、未删除的文章
        News newsEx = new News();
        newsEx.setIsPublish(1); //已发布
        newsEx.setDelFlag(0);   //未删除
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withIgnorePaths("isTop").withIgnorePaths("readNum");
        //创建查询实例
        Example<News> ex = Example.of(newsEx,matcher);
        //创建分页器
        Pageable pageable = PageRequest.of(0, 5);
        //查找当前页文章
        Page<News> newsPage = newsRepository.findAll(ex,pageable);
        List<News> newsList = new ArrayList<>();
        if (newsPage.getTotalElements() == 0) {
            resultMessage = "文章列表为空";
        }else {
            for (News news : newsPage) {
                newsList.add(news);
            }
        }

        return newsList;
    }

    //后台管理
    @RequestMapping("/indexB")

    public String indexB() {

        return "back/index";
    }

    //大图管理  后台
    @RequestMapping("/gallery")
    public String gallery() {

        return "back/gallery";
    }

    //大图保存  后台
    @RequestMapping("/gallerySave")
    public String gallerySave() {

        return "redirect:/gallery";
    }
}
