package com.zcj.ls.ls_web;

import com.zcj.ls.ls_web.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//jar打包方式
@SpringBootApplication
@EnableJpaAuditing	//启动jpaAuditing
public class LsWebApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(LsWebApplication.class, args);
		SpringUtil.setApplicationContext(applicationContext);
	}
}

//war打包方式
//@SpringBootApplication
//@EnableJpaAuditing	//启动jpaAuditing
//public class LsWebApplication extends SpringBootServletInitializer {
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(LsWebApplication.class);
//	}
//
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = SpringApplication.run(LsWebApplication.class, args);
//		SpringUtil.setApplicationContext(applicationContext);
//	}
//
//}
