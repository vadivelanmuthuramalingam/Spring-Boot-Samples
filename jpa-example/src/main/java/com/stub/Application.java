package com.stub;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ServletComponentScan

@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).run(args);
	}

}
