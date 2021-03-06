package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.StringConfig;
import com.zcj.ls.ls_web.dao.AboutUsRepository;
import com.zcj.ls.ls_web.entity.AboutUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * 关于我们
 */
@Controller
public class AboutUsController {

    //JPA实体
    private final AboutUsRepository aboutUsRepository;
    //存储查询结果说明：成功或者失败的原因
    private String resultMessage = "";

    @Autowired
    public AboutUsController(AboutUsRepository aboutUsRepository) {
        this.aboutUsRepository = aboutUsRepository;
    }

    /**
     * 关于我们  前台
     * 根据类型查找文章
     * @param type 文章类型
     * @return
     */
    @RequestMapping("/aboutUsDetail")
    public String aboutUsDetail(Model model, String type) {
        if (type == null) {
            type = StringConfig.profile;
        }
        Optional<AboutUs> aboutUs = aboutUsRepository.findByType(type);
        if (aboutUs == null) {
            resultMessage = "获取文章详情失败";
            aboutUs = Optional.of(new AboutUs());
            aboutUs.get().setType(type);
        }
        model.addAttribute("aboutUs", aboutUs.get());
        model.addAttribute("msg", resultMessage);
        model.addAttribute("page","aboutUsDetail");
        return "front/aboutUsDetail";
    }

    /**
     * 编辑内容  后台
     * 根据类型查找文章
     * @param model
     * @param type  文章类型
     * @return  profileEdit/planningEdit/
     */
    @RequestMapping(value = "/aboutUsEdit")
    public String aboutUsEdit(Model model, String type) {
        if (type == null) {
            type = StringConfig.profile;
        }
        Optional<AboutUs> aboutUs = aboutUsRepository.findByType(type);
        if (!aboutUs.isPresent()) {
            resultMessage = "获取文章详情失败";
            aboutUs = Optional.of(new AboutUs());
            aboutUs.get().setType(type);
        }
        model.addAttribute("aboutUs", aboutUs.get());
        model.addAttribute("msg", resultMessage);
        return "back/aboutUsEdit";
    }

    /**
     * 保存文章   后台
     * @param model
     * @param aboutUs 文章内容
     * @return 保存后的文章编辑页面
     */
    @PostMapping(value = "/aboutUsSave")
    public String aboutUsSave(Model model, AboutUs aboutUs) {
        aboutUs.setAuthor(StringConfig.author);
        Optional<AboutUs> old = aboutUsRepository.findByType(aboutUs.getType());
        if (!old.isPresent()) {
            AboutUs res = aboutUsRepository.save(aboutUs);
            if (res == null) {
                resultMessage = "保存失败";
            }
        }else {
            int res = aboutUsRepository.updateAboutUs(aboutUs.getAuthor(),aboutUs.getContent(),aboutUs.getType());
            if (res == 0) {
                resultMessage = "更新失败";
            }
        }
        model.addAttribute("aboutUs", aboutUs);
        model.addAttribute("msg", resultMessage);
        return "redirect:/aboutUsEdit";
    }
}
