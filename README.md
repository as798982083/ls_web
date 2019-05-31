# ls_web

## 版本说明：  
**数据库**：[mysql 5.5](https://pan.baidu.com/s/1vrWrR2iKoq80yJamQx7YeQ)   
**jdk**：[java version "1.8.0_91"](https://pan.baidu.com/s/1TtBNUSVVQSrenyg_9kRHsQ)  
**spring boot**: 2.0.3.RELEASE  
**数据库连接**：[mysql-connector-java-5.1.12-bin.jar 提取码：2tjp ](https://pan.baidu.com/s/1bAOEWXjewwJa0TUP0bAYag)   
由于该jar文件在maven库中不可用，因此要在本地添加依赖。


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
 
 **文件名**：驼峰命名法。eg：`newsShow.html`  
 **类名**：每个单词首字母大写。eg：`NewsController`  
 **方法名**： 驼峰命名法。eg： `public void newsShow(){}`  
 **路由命名**：驼峰命名法。eg：新闻列表页面`newsShow.html`，则跳转到新闻列表的路由为`@RequestMapping("/newsShow")`
 
jar包发布：ieda-mavenProject-lifecycle-install，目录的target下会生成.jar的项目文件。


## IDEA网站项目打jar包发布

[在IDEA中将SpringBoot项目打包成jar包并发布](https://blog.csdn.net/zhizhuodewo6/article/details/82178286)