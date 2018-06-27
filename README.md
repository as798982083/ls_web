# ls_web

## 版本说明：  
**数据库**：[mysql 5.5](https://pan.baidu.com/s/1vrWrR2iKoq80yJamQx7YeQ)   
**jdk**：[java version "1.8.0_91"](https://pan.baidu.com/s/1TtBNUSVVQSrenyg_9kRHsQ)  
**spring boot**: 2.0.3.RELEASE  


**spring boot配置 iframe同源可访问**:  
`@EnableWebSecurity  
 public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  
     @Override       
     protected void configure(HttpSecurity http) throws Exception {
         http  
            // ...  
            .headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();  
     }  
 }`   
 配置完成后，a标签的请求依然会被拦截，controller拦截返回的页面会在指定的iframe中显示。
 
 ## 命名规则
 
 **文件名**：全部小写，中间使用下划线"_"分割。eg：`news_show.html`  
 **类名**：每个单词首字母大写。eg：`NewsController`
 **方法名**： 驼峰命名法。eg： `public void newsDetail(){}`
 **路由命名**：与路由对应的页面同名。eg：新闻列表页面`news.html`，则跳转到新闻列表的路由为`@RequestMapping("/news")`
 