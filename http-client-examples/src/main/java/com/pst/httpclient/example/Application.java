package com.pst.httpclient.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;



import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"org.pst.httpclient.example", "com.pst.httpclient.builder"})
@EnableAutoConfiguration

@EnableSwagger2
@ServletComponentScan

public class Application extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(Application.class).run(args);
		}
		 	

}
