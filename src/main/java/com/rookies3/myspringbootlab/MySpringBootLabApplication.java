package com.rookies3.myspringbootlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringBootLabApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MySpringBootLabApplication.class, args);
		SpringApplication application = new SpringApplication(MySpringBootLabApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}
}
