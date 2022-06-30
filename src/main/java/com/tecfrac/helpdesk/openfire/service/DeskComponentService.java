package com.tecfrac.helpdesk.openfire.service;

import com.tecfrac.helpdesk.model.ModelUserAction;
import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.beans.UserBean;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;

public interface DeskComponentService {

    UserBean getValueByJID(String userJid);

    TicketBean getValueById(Long ticketId);

    Return<TicketBean> createTicket();

    Return<ModelUserAction> UserHasAction(Long userType, String action);

    Return<TicketBean> reopenTicket(Long ticketId);

}
