package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.StringConfig;
import com.zcj.ls.ls_web.dao.ActivityRepository;
import com.zcj.ls.ls_web.entity.Activity;
import com.zcj.ls.ls_web.utils.DateUtil;
import com.zcj.ls.ls_web.utils.FileUtil;
import com.zcj.ls.ls_web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
public class ActivityController {

    //存储查询结果说明：成功或者失败的原因
    private final ActivityRepository activityRepository;
    private String resultMessage = "";

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * 新闻列表  前台
     * @param model
     * @return
     */
    @RequestMapping("/activity")
    public String activity(Model model, Integer pageNum) {
        if (pageNum == null) {
            pageNum = 0;
        }
        //创建查询条件数据对象:只查找已发布、未删除的文章
        Activity activityEx = new Activity();
        activityEx.setIsPublish(1); //已发布
        activityEx.setDelFlag(0);   //未删除
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("isPublish", ExampleMatcher.GenericPropertyMatchers.exact()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("isTop").withIgnorePaths("readNum");
        //创建查询实例
        Example<Activity> ex = Example.of(activityEx,matcher);
        List<Activity> totalActivity = activityRepository.findAll(ex);
        int totalPages = (totalActivity.size() / 4);
        //创建分页器
        Pageable pageable = PageRequest.of(pageNum, 4);
        //查找当前页文章
        Page<Activity> activityPage = activityRepository.findAll(ex,pageable);
        List<Activity> activityList = new ArrayList<>();
        if (activityPage.getTotalElements() == 0) {
            resultMessage = "文章列表为空";
        }else {
            for (Activity activity : activityPage) {
                activityList.add(activity);
            }
        }
        Optional<Activity> activityTop = activityRepository.findByTop();
        //去除富文本的标签
        for(int i=0;i<activityList.size();i++) {
            Activity activity = activityList.get(i);
            String content = activity.getContent();
            content = StringUtil.delHTMLTag(content);
            activityList.get(i).setContent(content);
        }
        Activity activity = activityTop.get();
        String content = activity.getContent();
        content = StringUtil.delHTMLTag(content);
        activityTop.get().setContent(content);

        model.addAttribute("activityList", activityList);
        model.addAttribute("activityTop", activityTop.get());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","activity");
        return "front/activity";
    }

    /**
     * 新闻详情  前台
     * @param id 文章id
     * @return
     */
    @RequestMapping("/activityDetail")
    public String activityDetail(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Activity> activity = activityRepository.findById(targetId);
        if (activity == null) {
            resultMessage = "获取文章详情失败";
        }else{
            //更新阅读次数
            int readNum = activity.get().getReadNum() + 1;
            activityRepository.updateReadNum(readNum,activity.get().getId());
            activity.get().setReadNum(readNum);
        }
        model.addAttribute("activity", activity.get());
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","activity");
        return "front/activityDetail";
    }

    /**
     * 新闻列表  后台
     * @param model
     * @return
     */
    @RequestMapping("/activityList")
    public String activityShow(Model model) {
        List<Activity> activityList = activityRepository.findAll();
        if (activityList == null) {
            resultMessage = "文章列表为空";
            activityList = new ArrayList<Activity>();
        }
        //文章标题和正文去标签、截取前20个文字
        for(int i=0;i<activityList.size();i++) {
            Activity activity = activityList.get(i);
            String title = activity.getTitle();
            String content = activity.getContent();
            if (content.length() > 20) {
                content = StringUtil.delHTMLTag(content).substring(0,20)+"...";
            }
            if (title.length() > 20) {
                title = StringUtil.delHTMLTag(title).substring(0,20)+"...";
            }
            activityList.get(i).setContent(content);
            activityList.get(i).setTitle(title);
        }
        model.addAttribute("activityList", activityList);
        model.addAttribute("msg", resultMessage);
        return "back/activityList";
    }

    /**
     * 编辑新闻  页面跳转
     * @param model
     * @param id 文章id
     * @return 留在修改页
     */
    @RequestMapping("/activityEdit")
    public String activityEdit(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Activity> activity = activityRepository.findById(targetId);
        if (activity == null) {
            resultMessage = "获取文章详情失败";
        }
        model.addAttribute("activity", activity.get());
        model.addAttribute("msg", resultMessage);
        return "back/activityEdit";
    }

    /**
     * 保存新闻
     * 默认作者为：南京恒宇社会工作服务中心
     * @param activity
     * @param image
     * @return 返回文章列表页
     */
    @RequestMapping("/activitySave")
    public String activitySave(@ModelAttribute Activity activity, MultipartFile image, @Autowired HttpServletRequest request) {
        if (image != null) {
            activity.setImageUrl(FileUtil.saveFile(image,request));
        }
        activity.setAuthor(StringConfig.author);
        Optional<Activity> old = activityRepository.findById(activity.getId());
        if (!old.isPresent()) {
            activity.setAuthor("南京恒宇社会工作服务中心");
            activity.setCreateTime(DateUtil.getCurrentDate());
            activity.setUpdateTime(DateUtil.getCurrentDate());
            Activity resActivity = activityRepository.save(activity);
            if (resActivity == null) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
//            activitySetTop(activity);
        }else{
            activity.setUpdateTime(DateUtil.getCurrentDate());  //更新修改时间
            activity.setIsPublish(0);   //修改后的文章默认：不发布
            int res = activityRepository.updateActivity(activity.getTitle(), activity.getAuthor(), activity.getContent(),
                    activity.getImageUrl(),activity.getUpdateTime(),activity.getIsPublish(), activity.getId());
            if (res == 0) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
        }
        return "redirect:/activityList";
    }

    /**
     * 添加新闻  页面跳转
     * @return
     */
    @RequestMapping("/activityAdd")
    public String activityAdd() {
        return "back/activityAdd";
    }

    /**
     * 删除文章  页面跳转
     * @return
     */
    @RequestMapping("/activityDelete")
    public String activityDelete(@ModelAttribute Activity activity) {
        activity.setDelFlag(1);
        activityRepository.updateDelFlag(activity.getDelFlag(),activity.getId());
        return "redirect:/activityList";
    }

    /**
     * 更新头条状态
     * @param activity
     * @return 返回文章列表页
     */
    @RequestMapping("/activitySetTop")
    public String activitySetTop(@ModelAttribute Activity activity) {
        Optional<Activity> oldTop = activityRepository.findByTop();
        //原来有头条
        if(oldTop.isPresent()){
            //是本条新闻，则取消头条设置，并设置最新发布的一条为头条
            if(oldTop.get().getId() == activity.getId()){
                activity.setIsTop(0);
                int res1 = activityRepository.updateIsTop(activity.getIsTop(), activity.getId());

                List<Activity> activityList = activityRepository.findAll();
                activityList.get(0).setIsTop(1);
                int res2 = activityRepository.updateIsTop( activityList.get(0).getIsTop(),  activityList.get(0).getId());
            } else{
                //不是本条，则取消原有的头条，设置本条为头条
                oldTop.get().setIsTop(0);
                int res1 = activityRepository.updateIsTop(oldTop.get().getIsTop(), oldTop.get().getId());

                activity.setIsTop(1);
                int res2 = activityRepository.updateIsTop(activity.getIsTop(), activity.getId());
            }
        }else {
            activity.setIsTop(1);
            int res = activityRepository.updateIsTop(activity.getIsTop(), activity.getId());
        }
        return "redirect:/activityList";
    }

    /**
     * 更新发布状态
     * @param activity
     * @return 返回文章列表页
     */
    @RequestMapping("/activitySetPublish")
    public String activitySetPublish(@ModelAttribute Activity activity) {
        int stat = (activity.getIsPublish() == 1)? 0:1;
        activity.setIsPublish(stat);
        int res = activityRepository.updateIsPublish(activity.getIsPublish(), activity.getId());
        return "redirect:/activityList";
    }

    /**
     * 更新阅读次数
     * @param activity
     * @return 返回文章列表页
     */
    @RequestMapping("/activitySetReadNum")
    public String activitySetReadNum(@ModelAttribute Activity activity) {
        int stat = activity.getReadNum();
        activity.setIsPublish(stat++);
        int res = activityRepository.updateReadNum(activity.getReadNum(), activity.getId());
        return "redirect:/activityList";
    }



}
