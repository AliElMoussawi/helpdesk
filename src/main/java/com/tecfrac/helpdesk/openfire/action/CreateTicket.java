package com.tecfrac.helpdesk.openfire.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tecfrac.helpdesk.openfire.action.config.Action;
import com.tecfrac.helpdesk.openfire.beans.Bean;
import com.tecfrac.helpdesk.openfire.beans.RequestInfo;
import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.internal.reply.Failure;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;
import com.tecfrac.helpdesk.openfire.service.DeskComponentService;

public class CreateTicket implements Action {

    private static final Logger log = LoggerFactory.getLogger(CreateTicket.class);

    @Autowired
    DeskComponentService deskComponentService;

    @Override
    public Return<TicketBean> doAction(TicketBean context, RequestInfo requestInfo) {
        log.info("in create ticket action");
        Return<TicketBean> createdTicket = deskComponentService.createTicket();
        if (!createdTicket.isSuccessfull()) {
            log.info("unexpected Error");
            return new Failure<TicketBean>("create_ticket.unexpected_error");
        }
        return createdTicket;
    }

}
