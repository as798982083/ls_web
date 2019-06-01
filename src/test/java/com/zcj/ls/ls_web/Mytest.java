package com.zcj.ls.ls_web;

import com.zcj.ls.ls_web.config.WebConfig;
import com.zcj.ls.ls_web.utils.HttpUtil;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mytest {

    @Autowired
    private WebConfig webConfig;

    /**
     * 正则表达式测试
     */
    @Test
    public void testRegExp(){
        String content = "<p style=\"border:0px;margin-top:0.63em;margin-bottom:1.8em;padding:0px;counter-reset:list-1" +
                " 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0;color:#191919;font-family:" +
                "&quot;PingFang SC&quot;, Arial, 微软雅黑, 宋体, simsun,sans-serif;font-size:16px;\">如果说阿根廷的出局" +
                "是预料之中的结果，那么葡萄牙队的离开则是多少有些令人惋惜。虽然阿根廷和葡萄牙都是以1球的劣势小负给法国" +
                "和乌拉圭，但是两队在球场上所展现的内容却完全不一样。梅西领衔的阿根廷队站在法国队面前，输的是心服口服，" +
                "而C罗领军的葡萄牙队，则是被卡瓦尼的两次闪光，让球队4年的努力付之东流。</p> <p style=\"border:0px;margi" +
                "n-top:0.63em;margin-bottom:1.8em;padding:0px;counter-reset:list-1 0 list-2 0 list-3 0 list-4 0 list-5" +
                " 0 list-6 0 list-7 0 list-8 0 list-9 0;color:#191919;font-family:&quot;PingFang SC&quot;, Arial, 微软雅" +
                "黑, 宋体, simsun,sans-serif;font-size:16px;\">";
        String res = delHTMLTag(content);
        res = res.substring(0, 20) + "...";
        System.out.println(res);

    }

    /**
     * 去掉html标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 时间处理测试
     * @throws ParseException
     */
    @Test
    public void testDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String nowString = df.format(calendar.getTime());
        Date nowDate = df.parse(nowString);
        System.out.println(nowString);
    }

    /**
     * Json方法测试
     */
    @Test
    public void testRepository(){
        String str = "{\"cusId\":\"4028816b63680ec00163681559410005\",\"cusName\":\"李四\",\"cusMobile\":\"15893749273\"," +
                "\"address\":\"江苏省南京市溧水区\",\"areaFullName\":\"江苏省南京市溧水区\",\"areaCode\":\"320110\"," +
                "\"code\":1000,\"msg\":\"登陆成功\"}\n";
        JSONObject jsonObject = JSONObject.fromObject(str);
        System.out.println(jsonObject);
    }

    /**
     * 测试二层jsonobject数据
     */
    @Test
    public void testJsonArray(){
        JSONObject jsonObject = JSONObject.fromObject("{\n" +
                "    \"data\": {\n" +
                "        \"accessToken\": \"at.1a3n0q7c6wbv4ighc5bqxsjpbt6nqido-6bewl9spau-19458g6-nvhj7icct\",\n" +
                "        \"expireTime\": 1559990570006\n" +
                "    },\n" +
                "    \"code\": \"200\",\n" +
                "    \"msg\": \"操作成功!\"\n" +
                "}");
        JSONObject jsonObject1 = JSONObject.fromObject("{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"deviceSerial\": \"C77960127\",\n" +
                "            \"channelNo\": 1,\n" +
                "            \"deviceName\": \"恒宇养老服务中心\",\n" +
                "            \"hls\": \"http://hls01open.ys7.com/openlive/01dfe4425c5649e89598179363d5f629.m3u8\",\n" +
                "            \"hlsHd\": \"http://hls01open.ys7.com/openlive/01dfe4425c5649e89598179363d5f629.hd.m3u8\",\n" +
                "            \"rtmp\": \"rtmp://rtmp01open.ys7.com/openlive/01dfe4425c5649e89598179363d5f629\",\n" +
                "            \"rtmpHd\": \"rtmp://rtmp01open.ys7.com/openlive/01dfe4425c5649e89598179363d5f629.hd\",\n" +
                "            \"flvAddress\": \"https://flvopen.ys7.com:9188/openlive/01dfe4425c5649e89598179363d5f629.flv\",\n" +
                "            \"hdFlvAddress\": \"https://flvopen.ys7.com:9188/openlive/01dfe4425c5649e89598179363d5f629.hd.flv\",\n" +
                "            \"status\": 1,\n" +
                "            \"exception\": 0,\n" +
                "            \"ret\": \"200\",\n" +
                "            \"desc\": \"获取成功!\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"code\": \"200\",\n" +
                "    \"msg\": \"操作成功!\"\n" +
                "}");
        System.out.println(jsonObject);
    }

    /**
     * 获取token成功
     */
    @Test
    public void testGetToken(){
        JSONObject jsonObject = new JSONObject();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appKey", webConfig.getCameraAppKey());
        params.add("appSecret",webConfig.getCameraAppSecret());
        JSONObject result = HttpUtil.postData(webConfig.getCameraGetToken(), params);
        JSONObject data = (JSONObject) result.get("data");
        String token = (String) data.get("accessToken");
        System.out.println(token);
    }

    /**
     * 添加设备成功
     */
    @Test
    public void testAddDevice(){
        String url = "https://open.ys7.com/api/lapp/device/add";
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("accessToken","at.1a3n0q7c6wbv4ighc5bqxsjpbt6nqido-6bewl9spau-19458g6-nvhj7icct");
        params.add("deviceSerial", "C77960127");
        params.add("validateCode", "TYUUDV");
        JSONObject result = HttpUtil.postData(url, params);
        String code = (String) result.get("code");
        String msg = (String) result.get("msg");
        System.out.println(code);
        System.out.println(msg);
//        result ： {"code":"200","msg":"操作成功!"}
    }

    @Test
    public void deviceNameUpdate(){

    }
    @Test
    public void deviceEncryptOff(){

    }
    @Test
    public void liveVideoOpen(){

    }
    @Test
    public void getLiveAddress(){

    }
    @Test
    public void liveVideoClose(){

    }

}