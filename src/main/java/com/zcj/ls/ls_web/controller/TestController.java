package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试跳转用的路由
 */
@Controller
public class TestController {

    //测试页面
    @RequestMapping("/test")
    public String test(){

        return "back/test";
    }
}
