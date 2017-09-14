package com.ahfdkun.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ahfdkun.web.HelloController;

@SpringBootApplication(scanBasePackageClasses = HelloController.class)
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}
}
