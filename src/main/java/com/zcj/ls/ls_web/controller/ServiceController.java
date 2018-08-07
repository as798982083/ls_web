package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.WebConfig;
import com.zcj.ls.ls_web.entity.User;
import com.zcj.ls.ls_web.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 核心业务
 */
@Controller
public class ServiceController {

    @Autowired
    private WebConfig webConfig;

    /**
     * 获取服务大类和项目列表
     * @param model
     * @param categoryIndex 大类序号
     * @return  大类和项目列表
     */
    @RequestMapping("/service")
    public String service(Model model, Integer categoryIndex) {
        if (categoryIndex == null) {
            categoryIndex = 0;
        }

        JSONArray categoryList = ylGetServiceCategory();
        String categoryId = (String) ((JSONObject) categoryList.get(categoryIndex)).get("categoryId");
        JSONArray itemList = ylGetServiceItem(categoryId);

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("itemList", itemList);
        model.addAttribute("page","service");
        return "front/service";
    }

    /**
     * 用本网站的账号去登录养老平台
     * @param user 本网站账号
     * @return  返回登录信息
     */
    public JSONObject ylLogin(User user){
        //封装本网站账号信息
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cusId", "");
            jsonObject.put("cusName", "李四");
            jsonObject.put("cusMobile", "15893749273");
            jsonObject.put("address", "江苏省南京市溧水区");
        } catch (JSONException e) {
            System.out.println("Json对象封装失败！！！");
            e.printStackTrace();
        }
        //获取养老平台登录信息
        JSONObject result = HttpUtil.getData(webConfig.getYlLogin(), jsonObject);
        return result;
    }

    public JSONArray ylGetServiceCategory(){
        //封装
        JSONObject jsonObject = new JSONObject();
        JSONObject result = HttpUtil.getData(webConfig.getYlGetServiceCategory(), jsonObject);
        JSONArray categoryList = (JSONArray) result.get("categoryList");
        return categoryList;
    }

    public JSONArray ylGetServiceItem(String categoryId){
        //封装
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("categoryId", categoryId);
        JSONObject result = HttpUtil.getData(webConfig.getYlGetServiceItem(), jsonObject);
        JSONArray serviceItemList = (JSONArray) result.get("itemList");
//        String serviceItemId = (String) ((JSONObject) serviceItemList.get(3)).get("itemId");
        return serviceItemList;
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
