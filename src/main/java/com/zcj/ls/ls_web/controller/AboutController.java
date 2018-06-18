package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 关于我们页面路由
 */
@Controller
public class AboutController {

    @RequestMapping("/about")
    public String about(){

        return "front/about";
    }

    @RequestMapping("/aboutB")
    public String aboutB(){

        return "back/about";
    }

}
