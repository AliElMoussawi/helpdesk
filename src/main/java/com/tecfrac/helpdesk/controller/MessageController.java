package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicketMessage;
import com.tecfrac.helpdesk.service.TicketService1;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    TicketService1 ticketService1;

    @Autowired
    BeanSession beanSession;

    @RequestMapping(method = RequestMethod.GET, value = "/{ticketId}")
    public ResponseEntity<List<ModelTicketMessage>> getMessages(@PathVariable Long ticketId) throws Exception {
        List<ModelTicketMessage> allMessages = ticketService1.getTicketMessges(ticketId, beanSession.getUser());
        return new ResponseEntity<>(allMessages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/last/{ticketId}")//
    public ResponseEntity<BeanPair<Long, String>> getLastMessage(@PathVariable Long ticketId) throws Exception {
        BeanPair<Long, String> getLastMessages = ticketService1.getLastMessages(ticketId, beanSession.getUser());
        return new ResponseEntity<>(getLastMessages, HttpStatus.OK);
    }

}
