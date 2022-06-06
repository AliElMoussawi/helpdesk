/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.Configuration;

import com.tecfrac.helpdesk.Interceptor.RequestAuthenticationInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component//Stores and provides access to a list of interceptors. For each interceptor you can optionally specify one or more URL patterns it applies to. 
public class RequestHeaderInterceptorAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    RequestAuthenticationInterceptor requestHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderInterceptor).excludePathPatterns("/auth/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/user/**");
        // registry.addInterceptor(requestHeaderInterceptor).excludePathPatterns("/user/addUser/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
//here this code is interceptor configuraton on the whole code with the exception of the login fucntion 
