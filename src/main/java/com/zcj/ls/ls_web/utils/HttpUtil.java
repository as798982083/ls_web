package com.zcj.ls.ls_web.utils;

import com.zcj.ls.ls_web.config.WebConfig;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component  //关键1
public class HttpUtil {

    private static HttpUtil httpUtil;   //关键2

    @Autowired
    public WebConfig webConfig;

    // 关键3
    @PostConstruct
    public void init() {
        httpUtil = this;
        httpUtil.webConfig = this.webConfig;
    }

    /**
     * 调用外部接口
     * @param url  外部接口地址
     * @param body  传参
     * @return 返回的json对象
     */
    public static JSONObject getData(String url, JSONObject body){
        JSONObject jsonObject;  //需要返回的json对象
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers = new HttpHeaders();    //请求头
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);    //json格式
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        entity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String result = entity.getBody();
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    public static JSONObject postData(String url, MultiValueMap<String, String> params) {
        JSONObject jsonObject;  //需要返回的json对象
        HttpHeaders headers = new HttpHeaders();    //请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);    //json格式
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String result = response.getBody();
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

}
