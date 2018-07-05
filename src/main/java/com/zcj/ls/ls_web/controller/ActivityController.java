package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.StringConfig;
import com.zcj.ls.ls_web.dao.ActivityRepository;
import com.zcj.ls.ls_web.entity.Activity;
import com.zcj.ls.ls_web.utils.FileUtil;
import com.zcj.ls.ls_web.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 活动中心
 */
@Controller
public class ActivityController {

    private final ActivityRepository activityRepository;
    //存储查询结果说明：成功或者失败的原因
    private String resultMessage = "";

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * 活动列表  前台
     * @param model
     * @return
     */
    @RequestMapping("/activity")
    public String activity(Model model) {
        List<Activity> activityList = activityRepository.findAll();
        if (activityList == null) {
            resultMessage = "文章列表为空";
            activityList = new ArrayList<Activity>();
        }
        //去除富文本的标签
        for(int i=0;i<activityList.size();i++) {
            Activity activity = activityList.get(i);
            String content = activity.getContent();
            content = StringUtil.delHTMLTag(content);
            activityList.get(i).setContent(content);
        }
        model.addAttribute("activityList", activityList);
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","activity");
        return "front/activity";
    }

    /**
     * 活动详情  前台
     * @param id 文章id
     * @return
     */
    @RequestMapping("/activityDetail")
    public String activityDetail(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Activity> activity = activityRepository.findById(targetId);
        if (activity == null) {
            resultMessage = "获取文章详情失败";
        }
        model.addAttribute("activity", activity.get());
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","activity");
        return "front/activityDetail";
    }

    /**
     * 活动列表  后台
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
     * 编辑活动  页面跳转
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
     * 保存活动
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
            activity.setCreateTime(System.currentTimeMillis());
            Activity resActivity = activityRepository.save(activity);
            if (resActivity == null) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
        }else{
            int res = activityRepository.updateActivity(activity.getTitle(), activity.getAuthor(), activity.getContent(),activity.getImageUrl(), activity.getId());
            if (res == 0) {
                resultMessage = "保存失败";
            } else {
                resultMessage = "保存成功";
            }
        }
        return "redirect:/activityList";
    }

    /**
     * 添加活动  页面跳转
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
    public String activityDelete(String id) {
        Long targetId = Long.parseLong(id);
        activityRepository.deleteById(targetId);
        return "redirect:/activityList";
    }


}
