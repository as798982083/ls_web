package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){

        return "front/index";
    }

    @RequestMapping("/indexB")
    public String indexB(){

        return "back/index";
    }

}
