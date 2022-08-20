/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@Controller
@RequestMapping("/test")
public class TestController implements ApplicationContextAware {

    private ApplicationContext context;

    @PostMapping("/")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<Boolean> runningContextChecker() {
        boolean value = ((ConfigurableApplicationContext) context).isRunning();
        return new ResponseEntity<>(value, HttpStatus.OK);}
    

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;

    }
}
