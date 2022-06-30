/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.handler;

import com.tecfrac.helpdesk.bean.BeanNotificationMessage;
import com.tecfrac.helpdesk.bean.BeanSession;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UpdateAspect {

    @Autowired
    private BeanSession beanSession;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Around("execution(* com.tecfrac.helpdesk.repository.TicketRepository.save*(..)) or execution(* com.tecfrac.helpdesk.repository.TicketRepository.delete*(..))")
    public void actionOnTicket(JoinPoint jointPoint) {
        String action = (jointPoint.getSignature().getName().toLowerCase().contains("save")) ? "update" : "delete";
        System.out.println("com.tecfrac.helpdesk.handler.UpdateAspect.afterAnyRun() " + beanSession.getUser());
        List<ModelTicket> tickets = new ArrayList<>();
        if (jointPoint.getArgs()[0] instanceof ModelTicket) {
            tickets.add((ModelTicket) jointPoint.getArgs()[0]);
        } else {
            System.out.println("jointPoint.getArgs()[0]" + jointPoint.getArgs()[0]);
            List<ModelTicket> list = (List<ModelTicket>) jointPoint.getArgs()[0];
            for (ModelTicket t : list) {
                tickets.add(t);
            }
        }
        ModelUser notConcernedUser = beanSession.getUser();
        Map<ModelUser, List<ModelTicket>> usersConcerendTickets = new HashMap<>();
        for (ModelTicket ticket : tickets) {
            if (notConcernedUser != ticket.getRequester()) {
                List<ModelTicket> userTickts = usersConcerendTickets.getOrDefault(ticket.getRequester(), new ArrayList<>());
                userTickts.add(ticket);
                usersConcerendTickets.put(ticket.getRequester(), userTickts);
            }
            for (ModelUserGroup t : ticket.getAssignedGroup().getUser()) {
                if (notConcernedUser != t.getUser()) {
                    List<ModelTicket> userTickts = usersConcerendTickets.getOrDefault(t.getUser(), new ArrayList<>());
                    userTickts.add(ticket);
                    usersConcerendTickets.put(t.getUser(), userTickts);
                }
            }

            for (Map.Entry<ModelUser, List<ModelTicket>> usersTickets : usersConcerendTickets.entrySet()) {
                System.out.print("key :" + usersTickets.getKey());
                simpMessagingTemplate.convertAndSendToUser(usersTickets.getKey().getUsername(), "/topic/" + action, new BeanNotificationMessage(usersTickets.getValue(), action));
            }
        }
    }
}
