package com.zcj.ls.ls_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心业务
 */
@Controller
public class ServiceController {

    @RequestMapping("/service")
    public String service(Model model) {
        List categoryList = new ArrayList();
        categoryList.add("为小服务");
        categoryList.add("助残服务");
        categoryList.add("社区服务");
        categoryList.add("家庭服务");
        categoryList.add("居家养老");
        categoryList.add("志愿者服务");

        List<List> itemList = new ArrayList();
        List items1 = new ArrayList();
        items1.add("四点半课堂");
        List items2 = new ArrayList();
        List items3 = new ArrayList();
        List items4 = new ArrayList();
        items4.add("鲜花配送");
        items4.add("保洁");
        items4.add("搬家");
        items4.add("家电维修");
        items4.add("清洗抽油机");
        items4.add("钟点工");
        items4.add("下水道疏通");
        items4.add("月嫂");
        items4.add("白班保姆");
        items4.add("水果配送");
        items4.add("住家保姆");
        List items5 = new ArrayList();
        items5.add("日间照料");
        items5.add("住家照顾老人");
        items5.add("代煎中药");
        items5.add("代购生活用品");
        items5.add("陪老人聊天");
        items5.add("上门做餐（喂餐）");
        items5.add("一般家务");
        items5.add("上门修脚");
        items5.add("陪同散步");
        items5.add("康复护理");
        items5.add("一般咨询");
        items5.add("日间照料");
        items5.add("法律咨询");
        items5.add("白班照顾老人");
        List items6 = new ArrayList();
        items6.add("上门保洁");
        items6.add("上门送餐");

        itemList.add(items1);
        itemList.add(items2);
        itemList.add(items3);
        itemList.add(items4);
        itemList.add(items5);
        itemList.add(items6);


        model.addAttribute("categoryList", categoryList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("page","service");
        return "front/service";
    }

//    //JPA实体
//    private final AboutUsRepository aboutUsRepository;
//    //存储查询结果说明：成功或者失败的原因
//    private String resultMessage = "";
//
//    @Autowired
//    public ServiceController(AboutUsRepository aboutUsRepository) {
//        this.aboutUsRepository = aboutUsRepository;
//    }

//    /**
//     * 关于我们  前台
//     * 根据类型查找文章
//     * @param type 文章类型
//     * @return
//     */
//    @RequestMapping("/service")
//    public String aboutUsDetail(Model model, String type) {
//        if (type == null) {
//            type = StringConfig.profile;
//        }
//        Optional<AboutUs> aboutUs = aboutUsRepository.findByType(type);
//        if (aboutUs == null) {
//            resultMessage = "获取文章详情失败";
//            aboutUs = Optional.of(new AboutUs());
//            aboutUs.get().setType(type);
//        }
//        model.addAttribute("aboutUs", aboutUs.get());
//        model.addAttribute("msg", resultMessage);
//        return "front/aboutUsDetail";
//    }
//
//    /**
//     * 编辑内容  后台
//     * 根据类型查找文章
//     * @param model
//     * @param type  文章类型
//     * @return  profileEdit/planningEdit/
//     */
//    @RequestMapping(value = "/aboutUsEdit")
//    public String aboutUsEdit(Model model, String type) {
//        if (type == null) {
//            type = StringConfig.profile;
//        }
//        Optional<AboutUs> aboutUs = aboutUsRepository.findByType(type);
//        if (!aboutUs.isPresent()) {
//            resultMessage = "获取文章详情失败";
//            aboutUs = Optional.of(new AboutUs());
//            aboutUs.get().setType(type);
//        }
//        model.addAttribute("aboutUs", aboutUs.get());
//        model.addAttribute("msg", resultMessage);
//        return "back/aboutUsEdit";
//    }
//
//    /**
//     * 保存文章   后台
//     * @param model
//     * @param aboutUs 文章内容
//     * @return 保存后的文章编辑页面
//     */
//    @PostMapping(value = "/aboutUsSave")
//    public String aboutUsSave(Model model, AboutUs aboutUs) {
//        aboutUs.setAuthor(StringConfig.author);
//        Optional<AboutUs> old = aboutUsRepository.findByType(aboutUs.getType());
//        if (!old.isPresent()) {
//            AboutUs res = aboutUsRepository.save(aboutUs);
//            if (res == null) {
//                resultMessage = "保存失败";
//            }
//        }else {
//            int res = aboutUsRepository.updateAboutUs(aboutUs.getAuthor(),aboutUs.getContent(),aboutUs.getType());
//            if (res == 0) {
//                resultMessage = "更新失败";
//            }
//        }
//        model.addAttribute("aboutUs", aboutUs);
//        model.addAttribute("msg", resultMessage);
//        return "redirect:/aboutUsEdit";
//    }
}
