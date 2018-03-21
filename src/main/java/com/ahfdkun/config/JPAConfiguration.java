package com.ahfdkun.config;

import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
// 启动Spring事务
@EnableTransactionManagement(proxyTargetClass = true)
// 启动JPA并指定Repository接口
@EnableJpaRepositories(basePackages = "com.ahfdkun.repository")
@EntityScan(basePackages = "com.ahfdkun.domain")
public class JPAConfiguration {

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
