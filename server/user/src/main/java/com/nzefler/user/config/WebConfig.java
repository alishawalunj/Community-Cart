package com.nzefler.user.config;

import com.nzefler.user.filter.AuthValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<AuthValidationFilter> authFilter(AuthValidationFilter filter) {
        FilterRegistrationBean<AuthValidationFilter> bean = new FilterRegistrationBean<>(filter);
        bean.addUrlPatterns("/users/*");
        bean.setOrder(1);
        return bean;
    }
}
