package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.StringConfig;
import com.zcj.ls.ls_web.dao.NewsRepository;
import com.zcj.ls.ls_web.entity.News;
import com.zcj.ls.ls_web.utils.DateUtil;
import com.zcj.ls.ls_web.utils.FileUtil;
import com.zcj.ls.ls_web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 新闻中心
 */
@Controller
public class NewsController {

    //存储查询结果说明：成功或者失败的原因
    private final NewsRepository newsRepository;
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
    public String news(Model model, Integer pageNum) {
        if (pageNum == null) {
            pageNum = 0;
        }
        //创建查询条件数据对象:只查找已发布、未删除的文章
        News newsEx = new News();
        newsEx.setIsPublish(1); //已发布
        newsEx.setDelFlag(0);   //未删除
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("isPublish", ExampleMatcher.GenericPropertyMatchers.exact()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("isTop").withIgnorePaths("readNum");
        //创建查询实例
        Example<News> ex = Example.of(newsEx,matcher);
        List<News> totalNews = newsRepository.findAll(ex);
        int totalPages = (totalNews.size() / 3);
        //创建分页器
        Pageable pageable = PageRequest.of(pageNum, 3);
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
        Optional<News> newsTop = newsRepository.findByTop();
        //去除富文本的标签
        for(int i=0;i<newsList.size();i++) {
            News news = newsList.get(i);
            String content = news.getContent();
            content = StringUtil.delHTMLTag(content);
            newsList.get(i).setContent(content);
        }
        News news = newsTop.get();
        String content = news.getContent();
        content = StringUtil.delHTMLTag(content);
        newsTop.get().setContent(content);

        model.addAttribute("newsList", newsList);
        model.addAttribute("newsTop", newsTop.get());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","news");
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
        }else{
            //更新阅读次数
            int readNum = news.get().getReadNum() + 1;
            newsRepository.updateReadNum(readNum,news.get().getId());
            news.get().setReadNum(readNum);
        }
        model.addAttribute("news", news.get());
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","news");
        return "front/newsDetail";
    }

    /**
     * 新闻列表  后台
     * @param model
     * @return
     */
    @RequestMapping("/newsList")
    public String newsShow(Model model) {
        News newsEx = new News();
        newsEx.setDelFlag(0);   //未删除
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withIgnorePaths("isTop").withIgnorePaths("readNum").withIgnorePaths("isPublish");
        //创建查询实例
        Example<News> ex = Example.of(newsEx,matcher);
        //查找当前页文章
        List<News> newsList = newsRepository.findAll(ex);
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
        news.setAuthor(StringConfig.author);
        Optional<News> old = newsRepository.findById(news.getId());
        if (!old.isPresent()) {
            news.setAuthor("南京恒宇社会工作服务中心");
            news.setCreateTime(DateUtil.getCurrentDate());
            news.setUpdateTime(DateUtil.getCurrentDate());
            News resNews = newsRepository.save(news);
            if (resNews == null) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
//            newsSetTop(news);
        }else{
            news.setUpdateTime(DateUtil.getCurrentDate());  //更新修改时间
            news.setIsPublish(0);   //修改后的文章默认：不发布
            int res = newsRepository.updateNews(news.getTitle(), news.getAuthor(), news.getContent(),
                    news.getImageUrl(),news.getUpdateTime(),news.getIsPublish(), news.getId());
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
    public String newsDelete(@ModelAttribute News news) {
        news.setDelFlag(1);
        newsRepository.updateDelFlag(news.getDelFlag(),news.getId());
        return "redirect:/newsList";
    }

    /**
     * 更新头条状态
     * @param news
     * @return 返回文章列表页
     */
    @RequestMapping("/newsSetTop")
    public String newsSetTop(@ModelAttribute News news) {
        Optional<News> oldTop = newsRepository.findByTop();
        //原来有头条
        if(oldTop.isPresent()){
            //是本条新闻，则取消头条设置，并设置最新发布的一条为头条
            if(oldTop.get().getId() == news.getId()){
                news.setIsTop(0);
                int res1 = newsRepository.updateIsTop(news.getIsTop(), news.getId());

                List<News> newsList = newsRepository.findAll();
                newsList.get(0).setIsTop(1);
                int res2 = newsRepository.updateIsTop( newsList.get(0).getIsTop(),  newsList.get(0).getId());
            } else{
                //不是本条，则取消原有的头条，设置本条为头条
                oldTop.get().setIsTop(0);
                int res1 = newsRepository.updateIsTop(oldTop.get().getIsTop(), oldTop.get().getId());

                news.setIsTop(1);
                int res2 = newsRepository.updateIsTop(news.getIsTop(), news.getId());
            }
        }else {
            news.setIsTop(1);
            int res = newsRepository.updateIsTop(news.getIsTop(), news.getId());
        }
        return "redirect:/newsList";
    }

    /**
     * 更新发布状态
     * @param news
     * @return 返回文章列表页
     */
    @RequestMapping("/newsSetPublish")
    public String newsSetPublish(@ModelAttribute News news) {
        int stat = (news.getIsPublish() == 1)? 0:1;
        news.setIsPublish(stat);
        int res = newsRepository.updateIsPublish(news.getIsPublish(), news.getId());
        return "redirect:/newsList";
    }

    /**
     * 更新阅读次数
     * @param news
     * @return 返回文章列表页
     */
    @RequestMapping("/newsSetReadNum")
    public String newsSetReadNum(@ModelAttribute News news) {
        int stat = news.getReadNum();
        news.setIsPublish(stat++);
        int res = newsRepository.updateReadNum(news.getReadNum(), news.getId());
        return "redirect:/newsList";
    }



}
