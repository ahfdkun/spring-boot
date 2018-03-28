package com.ahfdkun.config;

import com.ahfdkun.config.security.*;
import com.ahfdkun.service.CustomUserDetailsService;
import com.ahfdkun.service.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableConfigurationProperties(SecuritySettings.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecuritySettings settings;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false); //remember me
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll() // 设置自定义登陆URL地址
                .successHandler(loginSuccessHandler())  // 设置登陆成功处理器
                .and().authorizeRequests()
                .antMatchers("/images/**", "/checkcode", "/scripts/**", "/styles/**").permitAll()
                .antMatchers(settings.getPermitall().split(",")).permitAll()
                .anyRequest().authenticated()
                .and().csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher()) // 跨站请求伪造
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().logout().logoutSuccessUrl(settings.getLogoutsuccssurl()) // 设置登出URL地址
                .and().exceptionHandling().accessDeniedPage(settings.getDeniedpage())
                .and().rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher() {
        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList<>();
        list.add("/rest/");
        csrfSecurityRequestMatcher.setExecludeUrls(list);
        return csrfSecurityRequestMatcher;
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl jtr = new JdbcTokenRepositoryImpl();
        jtr.setDataSource(dataSource);
        return jtr;
    }

    // 权限管理过滤器
    @Bean
    public CustomFilterSecurityInterceptor customFilter() throws Exception {
        CustomFilterSecurityInterceptor customFilter = new CustomFilterSecurityInterceptor();
        customFilter.setSecurityMetadataSource(new CustomSecurityMetadataSource(settings.getUrlroles())); // 权限配置资源管理器
        customFilter.setAccessDecisionManager(new CustomAccessDecisionManager()); // 权限管理决断器，对用户访问资源和用户拥有的角色权限进行对比
        customFilter.setAuthenticationManager(authenticationManager);
        return customFilter;
    }

}
