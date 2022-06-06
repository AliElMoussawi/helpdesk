/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelSession;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.request.RequestChangePassword;
import com.tecfrac.helpdesk.request.RequestLogin;
import com.tecfrac.helpdesk.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin
@Controller
@RequestMapping("/auth")
//@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BeanSession beanSession;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<ModelSession> login(@RequestBody RequestLogin request) throws Exception {
        ModelSession returnObject = authenticationService.login(request);
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public ResponseEntity<ModelUser> changePassword(@RequestBody RequestChangePassword newPassReq) throws Exception {

        ModelUser newUserInfo = authenticationService.changePassword(newPassReq);

        return new ResponseEntity<>(newUserInfo, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signOut")
    public ResponseEntity<String> signOut() throws Exception {

        authenticationService.signOut(beanSession.getId());
        return new ResponseEntity<>("signed out", HttpStatus.OK);

    }
}
