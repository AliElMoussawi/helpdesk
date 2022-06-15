package com.tecfrac.helpdesk.Configuration;

import com.tecfrac.helpdesk.Interceptor.CorsInterceptor;
import com.tecfrac.helpdesk.Interceptor.RequestAuthenticationInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component//Stores and provides access to a list of interceptors. For each interceptor you can optionally specify one or more URL patterns it applies to. 
public class RequestHeaderInterceptorAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    RequestAuthenticationInterceptor requestHeaderInterceptor;
    @Autowired
    CorsInterceptor corsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor);

        registry.addInterceptor(requestHeaderInterceptor).excludePathPatterns("/auth/login")
                .excludePathPatterns("/error")
                .excludePathPatterns("/user/**");//.excludePathPatterns("/ticket/**");
        // registry.addInterceptor(requestHeaderInterceptor).excludePathPatterns("/user/addUser/**");
    }

}
//here this code is interceptor configuraton on the whole code with the exception of the login fucntion 
