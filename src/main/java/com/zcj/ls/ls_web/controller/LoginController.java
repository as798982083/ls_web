package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录页路由
 * 后缀带“B”的，为后台管理页面跳转。
 */
@Controller
public class LoginController {

    //登录页   后台
    @RequestMapping("/loginB")
    public String index(Model model){
        model.addAttribute("page","index");
        return "back/login";
    }

    //登录验证
    @RequestMapping("/loginCheck")
    public String loginCheck(Model model, User user, HttpServletRequest request){
        if (user != null && ("admin").equals(user.getUserName()) && ("lsadmin").equals(user.getPassword())) {
            request.getSession().setAttribute("user",user);
            model.addAttribute("page","index");
            return "redirect:/indexB";
        }
        return "redirect:/loginB";
    }

}
