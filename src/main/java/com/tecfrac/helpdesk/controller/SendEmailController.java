package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.request.RequestMessageTicket;
import com.tecfrac.helpdesk.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SendEmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, value = "/sendmail")
    public String sendmail(@RequestBody RequestMessageTicket message) throws Exception {
        emailService.sendMail(message);
        return "emailsent";
    }
}
