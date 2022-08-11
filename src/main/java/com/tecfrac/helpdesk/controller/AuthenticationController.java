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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    BeanSession beanSession;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<ModelSession> login(@RequestBody RequestLogin request) throws Exception {
        ModelSession returnObject = authenticationService.login(request, beanSession);
        return new ResponseEntity<ModelSession>(returnObject, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test-web-socket")
    public ResponseEntity<ModelSession> tst() throws Exception {
//        simpMessagingTemplate.convertAndSend("/topic/messages", "new message1");
        simpMessagingTemplate.convertAndSendToUser("ali moussawi", "/topic/messages", "new message");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public ResponseEntity<ModelUser> changePassword(@RequestBody RequestChangePassword newPassReq) throws Exception {
        ModelUser newUserInfo = authenticationService.changePassword(newPassReq);
        return new ResponseEntity<ModelUser>(newUserInfo, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signOut")
    public ResponseEntity<String> signOut() throws Exception {
//        System.out.println("session Id sign out" + beanSession.getId());
        authenticationService.signOut(beanSession.getId(), beanSession);
        return new ResponseEntity<>("signed out", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/forgot")
    public ResponseEntity<String> processForgotPasswordForm(@RequestParam String email) {
        return new ResponseEntity<>("this email :" + email + " doesn't exist ", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<String> setNewPassword(@RequestParam String email, @RequestBody RequestChangePassword request) throws Exception {
        return new ResponseEntity<>("this email :" + email + " doesn't exist ", HttpStatus.BAD_REQUEST);
    }
}
