package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.request.RequestAddTicket;
import com.tecfrac.helpdesk.request.RequestDeleteTickets;
import com.tecfrac.helpdesk.request.RequestMergeTickets;
import com.tecfrac.helpdesk.service.TicketService;
import com.tecfrac.helpdesk.service.TicketService1;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@Controller
@RequestMapping("/v1/ticket")
public class TicketController1 {

    @Autowired
    BeanSession beanSession;
    @Autowired
    TicketService1 ticketService1;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<List<ModelTicket>> allTickets(@PathVariable Long id) throws Exception {
        List<ModelTicket> alltickets = ticketService1.allTicketsByStatusId1(id, beanSession.getUser());
        if (alltickets != null) {

            return new ResponseEntity<>(alltickets, HttpStatus.OK);
        }
        return new ResponseEntity<>(alltickets, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ModelTicket> addTicket(@RequestBody RequestAddTicket ticket) throws Exception {
        ModelTicket addTicket = ticketService1.addTicket(ticket, beanSession.getUser());
        return new ResponseEntity<>(addTicket, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/")//fix the update ascept for deletion
    public ResponseEntity<List<String>> deleteTicketsForever(@RequestBody RequestDeleteTickets tickets) throws Exception {
        List<String> deletedTickets = new ArrayList<>();
        for (Long ticketId : tickets.getTicketsIds()) {
            String deleted = ticketService1.deletedTicketsForever(ticketId).getId().toString();
            deletedTickets.add(deleted);
        }
        return new ResponseEntity<>(deletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stats")//need to fix it 
    public ResponseEntity<List<BeanPair<Long, Long>>> countTickets() throws Exception {
        List<BeanPair<Long, Long>> alltickets = ticketService1.countTickets(beanSession.getUser());
        return new ResponseEntity<>(alltickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/")//check all cases 
    public ResponseEntity<List<ModelTicket>> EditTicket(@RequestBody List<RequestAddTicket> tickets) throws Exception {
        List<ModelTicket> editedTickets = new ArrayList<>();
        for (RequestAddTicket ticket : tickets) {
            editedTickets.add(ticketService1.updateTicket(ticket, beanSession.getUser()));
        }
        return new ResponseEntity<>(editedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/merge")
    public ResponseEntity<ModelTicket> mergeTicket(@RequestBody RequestMergeTickets tickets) throws Exception {
        ModelTicket getTicketMessages = ticketService1.mergeTicketsInto(tickets.getPrimaryTicketId(), tickets.getMergeTicketId(), beanSession.getUser());
        return new ResponseEntity<>(getTicketMessages, HttpStatus.OK);
    }
}
