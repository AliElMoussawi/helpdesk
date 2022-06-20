package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelTicketMessage;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.request.RequestAddTicket;
import com.tecfrac.helpdesk.request.RequestDeleteTickets;
import com.tecfrac.helpdesk.service.AuthenticationService.Pair;
import com.tecfrac.helpdesk.service.TicketService;
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
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
@RequestMapping("/ticket")
//@RestController
public class TicketController {

    @Autowired
    BeanSession beanSession;
    @Autowired
    TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET, value = "/deletedTickets")
    public ResponseEntity<List<ModelTicket>> deletedTicket() throws Exception {
        List<ModelTicket> deletedTickets = ticketService.deletedTickets();
        return new ResponseEntity<>(deletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unsolvedTickets")
    public ResponseEntity<List<ModelTicket>> unsolvedTickets() throws Exception {
        List<ModelTicket> unSolvedTickets = ticketService.unSolvedTickets();
        return new ResponseEntity<>(unSolvedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/solvedTickets")//
    public ResponseEntity<List<ModelTicket>> solvedTickets() throws Exception {
        List<ModelTicket> SolvedTickets = ticketService.solvedTickets();
        return new ResponseEntity<>(SolvedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pendingTickets")//
    public ResponseEntity<List<ModelTicket>> pendingTickets() throws Exception {
        List<ModelTicket> PENDINGTICKETS = ticketService.pendingTickets();
        return new ResponseEntity<>(PENDINGTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/newTickets")
    public ResponseEntity<List<ModelTicket>> newTickets() throws Exception {
        List<ModelTicket> NEWTICKETS = ticketService.newGroupTickets();
        return new ResponseEntity<>(NEWTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/suspendedTickets")
    public ResponseEntity<List<ModelTicket>> suspendedTickets() throws Exception {
        List<ModelTicket> suspendedTICKETS = ticketService.suspendedTickets();
        return new ResponseEntity<>(suspendedTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTickets")
    public ResponseEntity<List<ModelTicket>> deleteTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
        List<ModelTicket> DeletedTickets = new ArrayList<ModelTicket>();
        for (Integer ticketId : tickets.getTicketsIds()) {
            ModelTicket DELETED = ticketService.deleteTicket(ticketId);
            DeletedTickets.add(DELETED);
        }
        return new ResponseEntity<>(DeletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updatedTickets")
    public ResponseEntity<List<ModelTicket>> updatedTickets() throws Exception {
        List<ModelTicket> recUpdatedTickets = ticketService.recUpdatedTickets();
        return new ResponseEntity<>(recUpdatedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unAssignedTickets")
    public ResponseEntity<List<ModelTicket>> unAssignedTickets() throws Exception {
        List<ModelTicket> unAssignedTickets = ticketService.unAssignedTickets();
        return new ResponseEntity<>(unAssignedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userUnSolvedTickets")//
    public ResponseEntity<List<ModelTicket>> userUnSolvedTickets() throws Exception {
        List<ModelTicket> userUnSolvedTickets = ticketService.userUnSolvedTickets(beanSession.getUser().getId());
        return new ResponseEntity<>(userUnSolvedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/undeleteTickets")
    public ResponseEntity<List<ModelTicket>> undeleteTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
        @SuppressWarnings("Convert2Diamond")
        List<ModelTicket> unDeletedTickets = new ArrayList<ModelTicket>();

        for (Integer ticketId : tickets.getTicketsIds()) {
            ModelTicket deleted = ticketService.undeleteTicket(ticketId);//changePassword(newPassReq);
            unDeletedTickets.add(deleted);
        }
        return new ResponseEntity<>(unDeletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/spamTickets")
    public ResponseEntity< List<ModelUser>> spamTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
        @SuppressWarnings("Convert2Diamond")
        List< ModelUser> spamTickets = new ArrayList< ModelUser>();
        for (Integer ticketId : tickets.getTicketsIds()) {
            Pair<ModelTicket, ModelUser> spam = ticketService.spamTicket(ticketId);
            spamTickets.add(spam.getSecond());
        }
        return new ResponseEntity<>(spamTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allTickets")
    public ResponseEntity<List<ModelTicket>> allTickets() throws Exception {
        List<ModelTicket> alltickets = ticketService.allTickets();
        return new ResponseEntity<>(alltickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addTicket")
    public ResponseEntity<ModelTicket> addTicket(@RequestBody RequestAddTicket ticket) throws Exception {
        ModelTicket addTicket = ticketService.addTicket(ticket);
        return new ResponseEntity<>(addTicket, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/editTicket/{id}")
    public ResponseEntity<ModelTicket> EditTicket(@PathVariable("id") String id, @RequestBody RequestAddTicket ticket) throws Exception {
        ModelTicket addTicket = null;
        if (!id.equals("null")) {
            addTicket = ticketService.updateTicket(Integer.parseInt(id), ticket);

            return new ResponseEntity<>(addTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(addTicket, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/statusTickets")
    public ResponseEntity<List<ModelTicket>> statusTickets(@RequestParam(value = "statusId") String statusId) throws Exception {
        Integer status = Integer.parseInt(statusId);
        List<ModelTicket> alltickets = ticketService.allTicketsByStatusId(status);
        return new ResponseEntity<>(alltickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTicketsForever")
    public ResponseEntity<List<String>> deleteTicketsForever(@RequestBody RequestDeleteTickets tickets) throws Exception {
        List<String> DeletedTickets = new ArrayList<String>();
        for (Integer ticketId : tickets.getTicketsIds()) {
            String DELETED = ticketService.deletedTicketsForever(ticketId);
            DeletedTickets.add(DELETED);
        }
        return new ResponseEntity<>(DeletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countTickets")
    public ResponseEntity<List<Pair<Integer, Integer>>> countTickets() throws Exception {
        //  Integer status = Integer.parseInt(statusId);
        List<Pair<Integer, Integer>> alltickets = ticketService.countTickets();
        System.out.println(alltickets);
        return new ResponseEntity<>(alltickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTicketMessages/{id}")//
    public ResponseEntity<List<ModelTicketMessage>> getTicketMessages(@PathVariable Integer id) throws Exception {
        List<ModelTicketMessage> getTicketMessages = ticketService.getTicketMessges(id);
        return new ResponseEntity<>(getTicketMessages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mergeTicketMessages/{id}")//
    public ResponseEntity<ModelTicket> mergeTicketMessages(@PathVariable Integer id, @RequestBody RequestDeleteTickets tickets) throws Exception {
        ModelTicket getTicketMessages = ticketService.mergeTicketsInto(id, tickets.getTicketsIds());
        return new ResponseEntity<>(getTicketMessages, HttpStatus.OK);
    }

}
