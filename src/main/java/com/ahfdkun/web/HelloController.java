package com.ahfdkun.web;

import com.ahfdkun.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/hello")
	public String hello() {
        logger.info("student.name: " + name);
        logger.info("user : " + userRepository.findAll());
		return "Hello World";
	}
}
