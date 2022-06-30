package com.tecfrac.helpdesk.controller;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelTicketMessage;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.request.RequestAddTicket;
import com.tecfrac.helpdesk.request.RequestDeleteTickets;
import com.tecfrac.helpdesk.service.TicketService;
import com.tecfrac.helpdesk.service.TicketService1;
import com.tecfrac.helpdesk.service.UserService;
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
@RequestMapping("/ticket")
public class TicketController {}/*

    @Autowired
    BeanSession beanSession;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;
    @Autowired
    TicketService1 ticketService1;

    @RequestMapping(method = RequestMethod.GET, value = "/deletedTickets")
    public ResponseEntity<List<ModelTicket>> deletedTicket() throws Exception {
        List<ModelTicket> deletedTickets = ticketService.deletedTickets(beanSession.getUser());
        return new ResponseEntity<>(deletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unsolvedTickets")
    public ResponseEntity<List<ModelTicket>> unsolvedTickets() throws Exception {
        List<ModelTicket> unSolvedTickets = ticketService.unSolvedTickets(beanSession.getUser());
        return new ResponseEntity<>(unSolvedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/solvedTickets")//
    public ResponseEntity<List<ModelTicket>> solvedTickets() throws Exception {
        List<ModelTicket> SolvedTickets = ticketService.solvedTickets();
        return new ResponseEntity<>(SolvedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pendingTickets")//
    public ResponseEntity<List<ModelTicket>> pendingTickets() throws Exception {
        List<ModelTicket> PENDINGTICKETS = ticketService.pendingTickets(beanSession.getUser());
        return new ResponseEntity<>(PENDINGTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/newTickets")
    public ResponseEntity<List<ModelTicket>> newTickets() throws Exception {
        List<ModelTicket> NEWTICKETS = ticketService.newGroupTickets(beanSession.getUser());
        return new ResponseEntity<>(NEWTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/suspendedTickets")
    public ResponseEntity<List<ModelTicket>> suspendedTickets() throws Exception {
        List<ModelTicket> suspendedTICKETS = ticketService.suspendedTickets(beanSession.getUser());
        return new ResponseEntity<>(suspendedTICKETS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTickets")
    public ResponseEntity<List<ModelTicket>> deleteTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
        List<ModelTicket> DeletedTickets = new ArrayList<>();
        for (Long ticketId : tickets.getTicketsIds()) {
            ModelTicket DELETED = ticketService.deleteTicket(ticketId);
            DeletedTickets.add(DELETED);
        }
//        simpMessagingTemplate.convertAndSendToUser("ali moussawi", "/topic/deletedtickets", "delete ticket");
//        sendMessages(getAllUsers(), "/topic/deletedtickets", "delete tickets");
        return new ResponseEntity<>(DeletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/updatedTickets")
    public ResponseEntity<List<ModelTicket>> updatedTickets() throws Exception {
        List<ModelTicket> recUpdatedTickets = ticketService.recUpdatedTickets();
        return new ResponseEntity<>(recUpdatedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unAssignedTickets")
    public ResponseEntity<List<ModelTicket>> unAssignedTickets() throws Exception {
        List<ModelTicket> unAssignedTickets = ticketService.unAssignedTickets(beanSession.getUser());
        return new ResponseEntity<>(unAssignedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userUnSolvedTickets")//
    public ResponseEntity<List<ModelTicket>> userUnSolvedTickets() throws Exception {
        List<ModelTicket> userUnSolvedTickets = ticketService.userUnSolvedTickets(beanSession.getUser());
        return new ResponseEntity<>(userUnSolvedTickets, HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.PATCH, value = "/undeleteTickets")
//    public ResponseEntity<List<ModelTicket>> undeleteTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
//        @SuppressWarnings("Convert2Diamond")
//        List<ModelTicket> unDeletedTickets = new ArrayList<ModelTicket>();
//
//        for (Long ticketId : tickets.getTicketsIds()) {
//            ModelTicket deleted = ticketService.undeleteTicket(ticketId);//changePassword(newPassReq);
//            unDeletedTickets.add(deleted);
//        }
//
//        return new ResponseEntity<>(unDeletedTickets, HttpStatus.OK);
//    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/spamTickets")
    public ResponseEntity< List<ModelUser>> spamTickets(@RequestBody RequestDeleteTickets tickets) throws Exception {
        @SuppressWarnings("Convert2Diamond")
        List< ModelUser> spamTickets = new ArrayList< ModelUser>();
        for (Long ticketId : tickets.getTicketsIds()) {
            BeanPair<ModelTicket, ModelUser> spam = ticketService.spamTicket(ticketId, beanSession.getUser());
            spamTickets.add(spam.getSecond());
        }
        return new ResponseEntity<>(spamTickets, HttpStatus.OK);
    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/allTickets")
//    public ResponseEntity<List<ModelTicket>> allTickets() throws Exception {
//        List<ModelTicket> alltickets = ticketService.allTickets();
//        return new ResponseEntity<>(alltickets, HttpStatus.OK);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/addTicket")
    public ResponseEntity<ModelTicket> addTicket(@RequestBody RequestAddTicket ticket) throws Exception {
        ModelTicket addTicket = ticketService.addTicket(ticket);

        return new ResponseEntity<>(addTicket, HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.PATCH, value = "/editTicket/{id}")
//    public ResponseEntity<ModelTicket> EditTicket(@PathVariable("id") String id, @RequestBody RequestAddTicket ticket) throws Exception {
//        ModelTicket addTicket = null;
//        if (!id.equals("null")) {
//            addTicket = ticketService.updateTicket(Long.parseInt(id), ticket);
//
//            return new ResponseEntity<>(addTicket, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(addTicket, HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(method = RequestMethod.GET, value = "/statusTickets")
//    public ResponseEntity<List<ModelTicket>> statusTickets(@RequestParam(value = "statusId") String statusId) throws Exception {
//        Long status = Long.parseInt(statusId);
//        List<ModelTicket> alltickets = ticketService.allTicketsByStatusId(status);
//        return new ResponseEntity<>(alltickets, HttpStatus.OK);
//    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteTicketsForever")
    public ResponseEntity<List<String>> deleteTicketsForever(@RequestBody RequestDeleteTickets tickets) throws Exception {
        List<String> DeletedTickets = new ArrayList<>();
        for (Long ticketId : tickets.getTicketsIds()) {
            String deleted = ticketService.deletedTicketsForever(ticketId, beanSession.getUser()).getId().toString();
            DeletedTickets.add(deleted);
        }
        return new ResponseEntity<>(DeletedTickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/countTickets")
    public ResponseEntity<List<BeanPair<Long, Long>>> countTickets() throws Exception {
        //  Long status = Long.parseInt(statusId);
        List<BeanPair<Long, Long>> alltickets = ticketService1.countTickets(beanSession.getUser());

        return new ResponseEntity<>(alltickets, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getTicketMessages/{id}/uI:{userId}")//
    public ResponseEntity<List<ModelTicketMessage>> getTicketMessages(@PathVariable Long id, @PathVariable Long userId) throws Exception {
        List<ModelTicketMessage> getTicketMessages = ticketService.getTicketMessges(id);
        return new ResponseEntity<>(getTicketMessages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getLastMessage/{id}/uI:{userId}")//
    public ResponseEntity<BeanPair<Long, String>> getLastMessage(@PathVariable Long id, @PathVariable Long userId) throws Exception {
        BeanPair<Long, String> getLastMessages = ticketService.getLastMessages(id);
        return new ResponseEntity<>(getLastMessages, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mergeTicketMessages/{id}")//
    public ResponseEntity<ModelTicket> mergeTicketMessages(@PathVariable Long id, @RequestBody RequestDeleteTickets tickets) throws Exception {
        ModelTicket getTicketMessages = ticketService.mergeTicketsInto(id, tickets.getTicketsIds(), beanSession.getUser());
        return new ResponseEntity<>(getTicketMessages, HttpStatus.OK);
    }

}
*/