package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.dao.ContectUsRepository;
import com.zcj.ls.ls_web.entity.ContectUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 联系我们页面路由
 */

@Controller
public class ContectUsController {

    private ContectUsRepository contectUsRepository;
    private String resultMessage = "";

    @Autowired
    public ContectUsController(ContectUsRepository contectUsRepository) {
        this.contectUsRepository = contectUsRepository;
    }

    //前台显示
    @RequestMapping("/contectUs")
    public String contectUs(ContectUs contectUs, Model model) {
        contectUs = contectUsRepository.findByTitle("联系我们");
        model.addAttribute("contectUs",contectUs);
        model.addAttribute("msg",resultMessage);
        return "front/contectUs";
    }

    //后台显示
    @RequestMapping(value = "/contectUsShow")
    public String contectUsShow(ContectUs contectUs, Model model) {
        contectUs = contectUsRepository.findByTitle("联系我们");
        model.addAttribute("contectUs",contectUs);
        model.addAttribute("msg",resultMessage);
        return "back/contectUs";
    }

    //编辑内容
    @RequestMapping(value = "/contectUsEdit",method = RequestMethod.POST)
    public String contectUsEdit(@ModelAttribute ContectUs contectUs) {
        contectUs.setTitle("联系我们"); //固定不变
        contectUs.setAuthor("南京恒宇社会工作服务中心");    //暂时固定

        ContectUs cus;
        cus = contectUsRepository.findByTitle("联系我们");
        if(cus == null){
            contectUsRepository.save(contectUs);
        }else {
            int resCode = contectUsRepository.updateContectUs(contectUs.getCompanyName(),
                    contectUs.getCompanyAddress(),
                    contectUs.getCompanyPhone(),
                    contectUs.getPeoplePhone(),
                    "联系我们");
        }
        return "redirect:/contectUsShow";
    }
}
