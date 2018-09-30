package com.tensquare.friend;

import com.tensquare.friend.config.JwtIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtIntercept intercept;

    public void addInterceptors(InterceptorRegistry registry) {
        //拦截出登录外的所有请求
        registry.addInterceptor(intercept)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }

}
