package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestMessageTicket;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    private BeanSession beanSession;

    @Autowired
    private UserRepository userRepository;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public SimpleMailMessage sendMail(RequestMessageTicket requestMessageTicket) {
        ModelUser SendToUser = userRepository.findById(requestMessageTicket.getRequesterId()).get();
        if (SendToUser != null) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userRepository.findById(requestMessageTicket.getRequesterId()).get().getEmail());
            mailMessage.setSubject(requestMessageTicket.getSubject());
            mailMessage.setText(requestMessageTicket.getMessage());
            mailMessage.setCc(userRepository.findById(requestMessageTicket.getccId()).get().getEmail());
            mailMessage.setSentDate(new Date());
            mailMessage.setFrom(beanSession.getUser().getEmail());
            javaMailSender.send(mailMessage);
            return mailMessage;
        }
        return null;
    }
}
