package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.dao.NewsRepository;
import com.zcj.ls.ls_web.entity.News;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * 关于我们页面路由
 */
@Controller
public class NewsController {


    private NewsRepository newsRepository;
    //存储查询结果说明：成功或者失败的原因
    private String resultMessage = "";

    //新闻列表  前台显示
    @RequestMapping("/news")
    public String news(Model model){
        List<News> newsList = newsRepository.findAll();
        if (newsList == null) {
            resultMessage = "文章列表为空";
        }
        model.addAttribute("newsList", newsList);
        model.addAttribute("msg", resultMessage);
        return "front/news";
    }

    //新闻详情  前台展示
    @RequestMapping("/newsDetail")
    public String newsDetail(){

        return "front/newsDetail";
    }

    //新闻列表  后台显示
    @RequestMapping("/newsShow")
    public String newsShow(Model model){
        List<News> newsList = newsRepository.findAll();
        if (newsList == null) {
            resultMessage = "文章列表为空";
        }
        model.addAttribute("newsList", newsList);
        model.addAttribute("msg", resultMessage);
        return "back/newsShow";
    }

    //新闻详情  后台显示
    @RequestMapping("/newsEdit")
    public String newsEdit(Model model, String id){
        Optional<News> news = newsRepository.findById(Long.parseLong(id));
        if (news == null) {
            resultMessage = "获取文章详情失败";
        }
        model.addAttribute("news", news);
        model.addAttribute("msg", resultMessage);
        return "newsEdit";
    }

    //保存
    @RequestMapping("/newsSave")
    public String newsSave(@ModelAttribute News news){
        News resNews = newsRepository.save(news);
        if (resNews == null) {
            resultMessage = "保存失败";
        } else {
            resultMessage = "保存成功";
        }
        return "redirect:/newsShow";
    }



}
