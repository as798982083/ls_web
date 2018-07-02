package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.dao.NewsRepository;
import com.zcj.ls.ls_web.entity.News;
import com.zcj.ls.ls_web.utils.FileUtil;
import com.zcj.ls.ls_web.utils.SpringUtil;
import com.zcj.ls.ls_web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * 新闻中心
 */
@Controller
public class NewsController {

    private final NewsRepository newsRepository;
    //存储查询结果说明：成功或者失败的原因
    private String resultMessage = "";

    @Autowired
    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    /**
     * 新闻列表  前台
     * @param model
     * @return
     */
    @RequestMapping("/news")
    public String news(Model model) {
        List<News> newsList = newsRepository.findAll();
        if (newsList == null) {
            resultMessage = "文章列表为空";
            newsList = new ArrayList<News>();
        }
        //去除富文本的标签
        for(int i=0;i<newsList.size();i++) {
            News news = newsList.get(i);
            String content = news.getContent();
            content = StringUtil.delHTMLTag(content);
            newsList.get(i).setContent(content);
        }
        model.addAttribute("newsList", newsList);
        model.addAttribute("msg", resultMessage);
        return "front/news";
    }

    /**
     * 新闻详情  前台
     * @param id 文章id
     * @return
     */
    @RequestMapping("/newsDetail")
    public String newsDetail(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<News> news = newsRepository.findById(targetId);
        if (news == null) {
            resultMessage = "获取文章详情失败";
        }
        model.addAttribute("news", news.get());
        model.addAttribute("msg", resultMessage);
        return "front/newsDetail";
    }

    /**
     * 新闻列表  后台
     * @param model
     * @return
     */
    @RequestMapping("/newsList")
    public String newsShow(Model model) {
        List<News> newsList = newsRepository.findAll();
        if (newsList == null) {
            resultMessage = "文章列表为空";
            newsList = new ArrayList<News>();
        }
        //文章标题和正文去标签、截取前20个文字
        for(int i=0;i<newsList.size();i++) {
            News news = newsList.get(i);
            String title = news.getTitle();
            String content = news.getContent();
            if (content.length() > 20) {
                content = StringUtil.delHTMLTag(content).substring(0,20)+"...";
            }
            if (title.length() > 20) {
                title = StringUtil.delHTMLTag(title).substring(0,20)+"...";
            }
            newsList.get(i).setContent(content);
            newsList.get(i).setTitle(title);
        }
        model.addAttribute("newsList", newsList);
        model.addAttribute("msg", resultMessage);
        return "back/newsList";
    }

    /**
     * 编辑新闻  页面跳转
     * @param model
     * @param id 文章id
     * @return 留在修改页
     */
    @RequestMapping("/newsEdit")
    public String newsEdit(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<News> news = newsRepository.findById(targetId);
        if (news == null) {
            resultMessage = "获取文章详情失败";
        }
        model.addAttribute("news", news.get());
        model.addAttribute("msg", resultMessage);
        return "back/newsEdit";
    }

    /**
     * 保存新闻
     * 默认作者为：南京恒宇社会工作服务中心
     * @param news
     * @param image
     * @return 返回文章列表页
     */
    @RequestMapping("/newsSave")
    public String newsSave(@ModelAttribute News news, MultipartFile image, @Autowired HttpServletRequest request) {
        if (image != null) {
            news.setImageUrl(FileUtil.saveFile(image,request));
        }
        Optional<News> old = newsRepository.findById(news.getId());
        if (!old.isPresent()) {
            news.setAuthor("南京恒宇社会工作服务中心");
            news.setCreateTime(System.currentTimeMillis());
            News resNews = newsRepository.save(news);
            if (resNews == null) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
        }else{
            int res = newsRepository.updateNews(news.getTitle(), news.getAuthor(), news.getContent(),news.getImageUrl(), news.getId());
            if (res == 0) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
        }
        return "redirect:/newsList";
    }

    /**
     * 添加新闻  页面跳转
     * @return
     */
    @RequestMapping("/newsAdd")
    public String newsAdd() {
        return "back/newsAdd";
    }

    /**
     * 删除文章  页面跳转
     * @return
     */
    @RequestMapping("/newsDelete")
    public String newsDelete(String id) {
        Long targetId = Long.parseLong(id);
        newsRepository.deleteById(targetId);
        return "redirect:/newsList";
    }


}
