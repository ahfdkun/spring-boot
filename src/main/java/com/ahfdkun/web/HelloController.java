package com.ahfdkun.web;

import com.ahfdkun.domain.Role;
import com.ahfdkun.domain.User;
import com.ahfdkun.repository.UserRedis;
import com.ahfdkun.repository.UserRepository;
import com.ahfdkun.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @Autowired
    private UserRedis userRedis;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/hello")
    public String hello(Principal user) throws ParseException {
        Authentication authentication = (Authentication)user;
        logger.info("Authentications: " + authentication.getAuthorities());
        logger.info("Credentials: " + authentication.getCredentials());
        logger.info("student.name: " + name);
        logger.info("findByNameAndCreatedate: " + userRepository.findByNameAndCreatedate("user", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-22 16:31:42")));
        logger.info("expand JPA: " + userRepository.findOne("user"));
        List<User> users = userRepository.findAll();
        logger.info("users: " + users);
        userRedis.add("abc", 100L, users.get(0));
        logger.info("redis user: " + userRedis.get("abc"));
        Role role = roleService.create(new Role("test redis cache"));
        logger.info("create role in mysql: " + role);
        logger.info("redis cache: " + roleService.findAll());
        logger.info("redis cache1111: " + roleService.findById(role.getId()));
        return "Hello World";
    }
}
