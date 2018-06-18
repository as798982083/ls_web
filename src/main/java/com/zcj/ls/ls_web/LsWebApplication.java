package com.zcj.ls.ls_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing	//启动jpaAuditing
public class LsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsWebApplication.class, args);
	}
}
