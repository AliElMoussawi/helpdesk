/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelSession;
import com.tecfrac.helpdesk.model.ModelUser;

import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestChangePassword;
import com.tecfrac.helpdesk.request.RequestLogin;

import com.tecfrac.helpdesk.service.AuthenticationService;
import com.tecfrac.helpdesk.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
@RequestMapping("/auth")
//@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

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
        System.out.println("session Id sign out"+beanSession.getId());
        authenticationService.signOut(beanSession.getId());
        return new ResponseEntity<>("signed out", HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot")
    public ResponseEntity<String> processForgotPasswordForm(@RequestParam String email) {
        SimpleMailMessage sendEmail = authenticationService.forgetPassword(email);
        if (sendEmail != null) {
            return new ResponseEntity<>("successMessage A password reset link has been sent to this : " + email, HttpStatus.OK);
        }
        return new ResponseEntity<>("this email :" + email + " doesn't exist ", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<String> setNewPassword(@RequestParam String email, @RequestBody RequestChangePassword request) throws Exception {
        SimpleMailMessage user = authenticationService.resetPassword(email, request.getNewPassword());
        if (user != null) {

            return new ResponseEntity<>("successMessage You have successfully reset your password.  You may now login.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("this email :" + email + " doesn't exist ", HttpStatus.BAD_REQUEST);
        }
    }
}
