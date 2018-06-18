package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后缀带“B”的，为后台管理页面跳转。
 */

@Controller
public class ContectUsController {

    @RequestMapping("/contectUs")
    public String contectUs() {

        return "front/contectUs";
    }

    @RequestMapping("/contectUsB")
    public String contectUsB() {

        return "back/contectUs";
    }

}
