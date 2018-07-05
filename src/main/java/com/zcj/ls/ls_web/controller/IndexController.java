package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页路由
 * 后缀带“B”的，为后台管理页面跳转。
 */
@Controller
public class IndexController {

    //前台显示
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("page","index");
        return "front/index";
    }

    //后台管理
    @RequestMapping("/indexB")
    public String indexB(){

        return "back/index";
    }

    //大图管理  后台
    @RequestMapping("/gallery")
    public String gallery(){

        return "back/gallery";
    }
    //大图保存  后台
    @RequestMapping("/gallerySave")
    public String gallerySave(){

        return "redirect:/gallery";
    }
}
