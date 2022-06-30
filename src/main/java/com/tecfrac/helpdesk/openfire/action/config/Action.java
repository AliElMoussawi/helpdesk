package com.tecfrac.helpdesk.openfire.action.config;

import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.beans.RequestInfo;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;

public interface Action {

    public Return<TicketBean> doAction(TicketBean context, RequestInfo requestInfo);
}
