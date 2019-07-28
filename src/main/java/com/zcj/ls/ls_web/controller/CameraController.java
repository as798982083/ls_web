package com.zcj.ls.ls_web.controller;

import com.zcj.ls.ls_web.config.WebConfig;
import com.zcj.ls.ls_web.dao.CameraRepository;
import com.zcj.ls.ls_web.entity.Camera;
import com.zcj.ls.ls_web.utils.DateUtil;
import com.zcj.ls.ls_web.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 首页路由
 * 后缀带“B”的，为后台管理页面跳转。
 */
@Controller
public class CameraController {

    @Autowired
    private WebConfig webConfig;

    //全局变量Token，每七天需要更新一次
    public String token = "at.1a3n0q7c6wbv4ighc5bqxsjpbt6nqido-6bewl9spau-19458g6-nvhj7icct";
    //直播地址
    public String liveAddress = "";

    //存储查询结果说明：成功或者失败的原因
    private final CameraRepository cameraRepository;
    private String resultMessage = "";

    public CameraController(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    /**
     * 摄像头列表
     * @param model
     * @param placeName 服务点名称
     * @return
     */
    @RequestMapping({"/cameraList","/emsys/equipment/camera/camera.do"})     //映射多个路径
    public String cameraList(Model model, String placeName, String action, String parentIframeId) {
        Camera cameraEx = new Camera();
        cameraEx.setDelFlag(0);   //未删除
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        //创建查询实例
        Example<Camera> ex = Example.of(cameraEx,matcher);
        //查找当前页列表
        List<Camera> cameraList = cameraRepository.findAll(ex);
        if (cameraList == null) {
            resultMessage = "摄像头列表为空";
            cameraList = new ArrayList<Camera>();
        }

        model.addAttribute("cameraList", cameraList);
        model.addAttribute("resultMessage", resultMessage);
        return "front/cameraList";
    }

    //视频直播
    @RequestMapping({"/cameraLive"})
    public String cameraLive(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Camera> camera = cameraRepository.findById(targetId);
        //1. 开通直播功能
        int resNum = liveVideoOpen(camera.get());
        //2. 获取直播地址
        resNum = getLiveAddress(camera.get());

        model.addAttribute("liveAddress", liveAddress);
        return "front/cameraLive";
    }

    //添加摄像头
    @RequestMapping({"/cameraAdd"})
    public String cameraAdd(Model model) {

        return "front/cameraAdd";
    }

    //保存摄像头信息
    @RequestMapping({"/cameraSave"})
    public String cameraSave(Model model, @ModelAttribute Camera camera, @Autowired HttpServletRequest request) {
        if (camera.getId() == null) {
            int resNum = addDevice(camera);
            /*
                0：添加设备失败，退出并发送失败消息。
                1：通过海康摄像头序列号和验证码添加设备成功
                2：通过直播地址添加设备成功
             */
            if (resNum == 0) {
                model.addAttribute("resultMessage", resultMessage);
                System.out.println(DateUtil.getCurrentDate() + ":" + resultMessage);
                return "front/result";
            } else if(resNum == 1) {
                //添加到海康摄像头到萤石平台成功，更新萤石云平台上的设备名称
                resNum = deviceNameUpdate(camera);
                /*
                    0：更新失败
                    1：更新成功
                */
                if (resNum == 0){
                    System.out.println(DateUtil.getCurrentDate()+":"+resultMessage);
                }
                camera.setCreateTime(DateUtil.getCurrentDate());
                camera.setDelFlag(0);
                Camera resCamera = cameraRepository.save(camera);
                if (resCamera == null) {
                    resultMessage += "添加到萤石平台成功，信息保存失败";
                } else {
                    resultMessage += "添加到萤石平台成功，信息保存成功";
                }
            } else {
                camera.setCreateTime(DateUtil.getCurrentDate());
                camera.setDelFlag(0);
                Camera resCamera = cameraRepository.save(camera);
                if (resCamera == null) {
                    resultMessage += "通过直播地址添加设备失败";
                } else {
                    resultMessage += "通过直播地址添加设备成功";
                }
            }
        }else{
            //更新设备名称
            int resNum = deviceNameUpdate(camera);
            if (resNum == 0){
                //更新设备名称失败，在后台打印失败信息
                System.out.println(DateUtil.getCurrentDate()+":"+resultMessage);
            }
            int res = cameraRepository.updateCamera(camera.getId(), camera.getPlaceName(), camera.getPlaceLevel(),camera.getContectsName(),
                    camera.getContectsPhone(),camera.getCameraSerialNum(),camera.getCameraValidateCode(),camera.getCameraAccount(),
                    camera.getCameraPassword(), camera.getCameraNum(), DateUtil.getCurrentDate(),camera.getLiveAddress());
            if (res == 0) {
                resultMessage = "摄像头信息更新失败";
            } else {
                resultMessage = "摄像头信息更新成功";
            }
        }

        model.addAttribute("resultMessage", resultMessage);
        System.out.println(DateUtil.getCurrentDate()+":"+resultMessage);
        return "front/result";
    }

    //获取token
    private void getToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appKey", webConfig.getCameraAppKey());
        params.add("appSecret",webConfig.getCameraAppSecret());
        JSONObject result = HttpUtil.postData(webConfig.getCameraGetToken(), params);
        JSONObject data = (JSONObject) result.get("data");
        token = (String) data.get("accessToken");
        System.out.println(token);
    }

    /*
        两种添加设备的方式：
        1、通过序列号和验证码添加设备
        2、通过直播地址添加设备
        如果（序列号、验证码）和（直播地址）都没有，则添加失败。
     */
    private int addDevice(Camera camera) {
        //通过海康摄像头的序列号和验证码添加设备
        if (!"".equals(camera.getCameraSerialNum()) && !"".equals(camera.getCameraValidateCode())) {
            String url = webConfig.getCameraAddDevice();
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("accessToken", token);
            params.add("deviceSerial", camera.getCameraSerialNum());    //序列号
            params.add("validateCode", camera.getCameraValidateCode()); //验证码
            JSONObject result = HttpUtil.postData(url, params);
            String code = (String) result.get("code");
            String msg = (String) result.get("msg");
            //accessToken异常或过期，重新获取accessToken，并再次添加设备
            if ("10002".equals(code)) {
                getToken();
                return addDevice(camera);
            } else if ("200".equals(code)) {
                resultMessage = "添加设备成功，可在萤石云平台查看设备";
                //添加设备成功后，关闭视频加密（否则无法直播视频）
                String url2 = webConfig.getCameraDeviceEncryptOff();
                JSONObject result2 = HttpUtil.postData(url2, params);
                String code2 = (String) result.get("code");
                String msg2 = (String) result.get("msg");
                if ("200".equals(code2)) {
                    resultMessage += "关闭视频加密成功!";
                } else {
                    resultMessage += "关闭视频加密失败！code2:" + code2 + "，msg2：" + msg2;
                }
                System.out.println(DateUtil.getCurrentDate() + " 添加设备到萤石平台成功后，关闭视频加密：" + result2.toString());
                return 1;
            } else {
                resultMessage = "添加设备失败! code:" + code + "，msg：" + msg;
                return 0;
            }
        }
        //通过直播地址添加设备
        else if (!"".equals(camera.getLiveAddress())) {

            return 2;
        }
        //暂时没有第三种添加方法
        else {
            return 0;
        }
    }

    //修改设备名
    private int deviceNameUpdate(Camera camera) {
        String url = webConfig.getCameraDeviceNameUpdate();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("accessToken",token);
        params.add("deviceSerial", camera.getCameraSerialNum());    //序列号
        params.add("deviceName", camera.getPlaceName()); //设备名称
        JSONObject result = HttpUtil.postData(url, params);
        String code = (String) result.get("code");
        String msg = (String) result.get("msg");
        //accessToken异常或过期，重新获取accessToken，并再次修改设备名
        if ("10002".equals(code)) {
            getToken();
            return addDevice(camera);
        }else if ("200".equals(code)) {
            resultMessage += "修改设备名称成功!";
            return 1;
        }else{
            resultMessage += "修改设备名称失败! code:"+code+"，msg："+msg+"!";
            return 0;
        }
    }

    //开通直播功能
    private int liveVideoOpen(Camera camera) {
        String url = webConfig.getCameraLiveVideoOpen();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("accessToken",token);
        params.add("source", camera.getCameraSerialNum()+":1");    //序列号:通道,序列号:通道
        JSONObject result = HttpUtil.postData(url, params);
        JSONArray data = (JSONArray) result.get("data");
        String code = (String) result.get("code");
        String msg = (String) result.get("msg");
        //accessToken异常或过期，重新获取accessToken，并再次开通直播功能
        if ("10002".equals(code)) {
            getToken();
            return addDevice(camera);
        }else if ("200".equals(code)) {
            resultMessage = "开通直播功能成功";
            return 1;
        }else{
            resultMessage = "开通直播功能失败! code:"+code+"，msg："+msg+",data:"+data;
            return 0;
        }
    }

    //获取直播地址
    private int getLiveAddress(Camera camera) {
        //如果保存了直播地址，则直接
        if ((camera.getLiveAddress()!= null && !"".equals(camera.getLiveAddress()))){
            liveAddress = camera.getLiveAddress();
            return 1;
        }else{
            String url = webConfig.getCameraGetLiveAddress();
            MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
            params.add("accessToken",token);
            params.add("source", camera.getCameraSerialNum()+":1");    //序列号:通道,序列号:通道
            JSONObject result = HttpUtil.postData(url, params);
            String code = (String) result.get("code");
            String msg = (String) result.get("msg");
            //accessToken异常或过期，重新获取accessToken，并再次开通直播功能
            if ("10002".equals(code)) {
                getToken();
                return addDevice(camera);
            }else if ("200".equals(code)) {
                resultMessage = "获取直播地址成功";
                //提取直播地址
                JSONArray data = (JSONArray) result.get("data");
                JSONObject liveData = (JSONObject) data.get(0);
                liveAddress = (String) liveData.get("hls"); //直播地址
                return 1;
            }else{
                resultMessage = "获取直播地址失败! code:"+code+"，msg："+msg;
                return 0;
            }
        }

    }

    /**
     * 关闭直播功能
     *
     */
//    /*@RequestMapping({"/liveVideoClose"})
//    private int liveVideoClose(Camera camera) {
//        String url = webConfig.getCameraLiveVideoClose();
//        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
//        params.add("accessToken",token);
//        params.add("source", camera.getCameraSerialNum()+":1");    //序列号:通道,序列号:通道
//        JSONObject result = HttpUtil.postData(url, params);
//        JSONArray data = (JSONArray) result.get("data");
//        String code = (String) result.get("code");
//        String msg = (String) result.get("msg");
//        //accessToken异常或过期，重新获取accessToken，并再次关闭直播功能
//        if ("10002".equals(code)) {
//            getToken();
//            return addDevice(camera);
//        }else if ("200".equals(code)) {
//            resultMessage = "关闭直播功能成功";
//            return 1;
//        }else{
//            resultMessage = "关闭直播功能失败! code:"+code+"，msg："+msg+",data:"+data;
//            return 0;
//        }
//    }*/

    //修改
    @RequestMapping({"/cameraEdit"})
    public String cameraEdit(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Camera> camera = cameraRepository.findById(targetId);
        if (!camera.isPresent()) {
            resultMessage = "获取摄像头信息失败";
            model.addAttribute("resultMessage", resultMessage);
            return "front/result";
        }
        model.addAttribute("camera", camera.get());
        return "front/cameraEdit";
    }

    //删除
    @RequestMapping({"/cameraDelete"})     //映射多个路径
    public String cameraDelete(Model model, String id) {
        Long targetId = Long.parseLong(id);
        Optional<Camera> camera = cameraRepository.findById(targetId);
        camera.get().setDelFlag(1);
        cameraRepository.updateDelFlag(camera.get().getDelFlag(),camera.get().getId());

        resultMessage = "摄像头信息逻辑删除成功；在萤石平台中依然可以查看";
        System.out.println(DateUtil.getCurrentDate()+":"+resultMessage);
        return "redirect:/cameraList";
    }
}
