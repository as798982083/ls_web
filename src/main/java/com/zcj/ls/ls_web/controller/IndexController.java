package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页路由
 * 后缀带“B”的，为后台管理页面跳转。
 */
@Controller
public class IndexController {

    //前台显示
    @RequestMapping("/index")
    public String index(){

        return "front/index";
    }

    //后台管理
    @RequestMapping("/indexB")
    public String indexB(){

        return "back/index";
    }

    //后台管理
    @RequestMapping("/moduleSetting")
    public String moduleSetting(){

        return "back/moduleSetting";
    }



}
