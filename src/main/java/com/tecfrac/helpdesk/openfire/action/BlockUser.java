package com.tecfrac.helpdesk.openfire.action;

import com.tecfrac.helpdesk.openfire.action.config.Action;
import com.tecfrac.helpdesk.openfire.beans.Bean;
import com.tecfrac.helpdesk.openfire.beans.RequestInfo;
import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.beans.UserBean;
import com.tecfrac.helpdesk.openfire.internal.reply.Failure;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;

public class BlockUser implements Action {

    @Override
    public Return<TicketBean> doAction(TicketBean context, RequestInfo requestInfo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

