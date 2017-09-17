package com.ahfdkun.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Value("${student.name}")
	private String name;
	
	@Value("#{'abcd'.length()}") // SpEL表达式
	private int age;
	
	@Value("${student.desc}")
	private String desc;
	
	@Value("${student.random.long}")
	private long random_long;
	
	@Value("${student.random.int1}")
	private int random_int1;
	
	@Value("${student.random.int2}")
	private int random_int2;

	@RequestMapping("/hello")
	public String hello() {
		System.out.println("student.name: " + name);
		return "Hello World";
	}
}
