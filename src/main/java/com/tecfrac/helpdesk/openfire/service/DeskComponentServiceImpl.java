package com.tecfrac.helpdesk.openfire.service;

import com.tecfrac.helpdesk.model.ModelAction;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelTicketStatus;
import com.tecfrac.helpdesk.model.ModelUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tecfrac.helpdesk.model.ModelUserAction;
import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.beans.UserBean;
import com.tecfrac.helpdesk.openfire.internal.reply.Failure;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;
import com.tecfrac.helpdesk.openfire.internal.reply.Success;
import com.tecfrac.helpdesk.repository.ActionRepository;
import com.tecfrac.helpdesk.repository.TicketRepository;
import com.tecfrac.helpdesk.repository.TicketStatusRepository;
import com.tecfrac.helpdesk.repository.UserActionRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeskComponentServiceImpl implements DeskComponentService {

    private static final Logger log = LoggerFactory.getLogger(DeskComponentServiceImpl.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    UserActionRepository userActionRepository;

    @Autowired
    TicketStatusRepository ticketStatusRepository;

    @Override
    @Transactional
    public UserBean getValueByJID(String userJid) {
        ModelUser result;
        try {
            result = userRepository.findByUsername(userJid);
        } catch (Exception e) {
            return null;
        }
        return UserBean.fromModel(result);
    }

    @Override
    @Transactional
    public TicketBean getValueById(Long ticketId) {
        ModelTicket result;
        try {
            result = ticketRepository.findById(ticketId).get();
        } catch (Exception e) {
            return null;
        }
        return TicketBean.fromModel(result
        );

    }

    @Override
    @Transactional

    public Return<TicketBean> createTicket() {

        log.info("create ticket");
        ModelTicket ticket = new ModelTicket();
        System.out.println("creating ticket");
        ticketRepository.save(ticket);
        return new Success<>(TicketBean.fromModel(ticket));
    }

    @Override
    @Transactional
    public Return<ModelUserAction> UserHasAction(Long userType, String actionValue) {
        ModelAction action = actionRepository.findByAction(actionValue);

        ModelUserAction userAction = userActionRepository.findByUserTypeIdAndActionId(userType, action.getId());
        if (userAction == null) {
            return new Failure<>("unauthorized");
        }
        return new Success<>(userAction);

    }

    @Override
    @Transactional
    public Return<TicketBean> reopenTicket(Long ticketId) {
        log.info("re-open ticket");
        Optional<ModelTicket> ticket = ticketRepository.findById(ticketId);
        if (ticket == null) {
            return new Failure<>("ticket.not_found");
        }
        ticket.get().setValid(true);
        ticket.get().setStatus(ticketStatusRepository.findById(ModelTicketStatus.OPEN).get());
        ticketRepository.save(ticket.get());
        return new Success<>(TicketBean.fromModel(ticket.get()));
    }

}
