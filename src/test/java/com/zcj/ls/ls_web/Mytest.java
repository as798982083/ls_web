package com.zcj.ls.ls_web;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mytest {

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

    @Test
    public void testDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String nowString = df.format(calendar.getTime());
        Date nowDate = df.parse(nowString);
        System.out.println(nowString);
    }
}