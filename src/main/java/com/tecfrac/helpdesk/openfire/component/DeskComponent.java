package com.tecfrac.helpdesk.openfire.component;

import com.tecfrac.helpdesk.DeskPlugin;
import com.tecfrac.helpdesk.model.ModelUserAction;
import com.tecfrac.helpdesk.openfire.action.config.Action;
import com.tecfrac.helpdesk.openfire.action.config.ActionsMapper;
import com.tecfrac.helpdesk.openfire.beans.RequestInfo;
import com.tecfrac.helpdesk.openfire.beans.TicketBean;
import com.tecfrac.helpdesk.openfire.beans.UserBean;
import com.tecfrac.helpdesk.openfire.internal.reply.Return;
import com.tecfrac.helpdesk.openfire.service.DeskComponentService;
import com.tecfrac.helpdesk.openfire.utils.IQUtils;
import java.util.Objects;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.jivesoftware.openfire.component.InternalComponentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmpp.component.AbstractComponent;
import org.xmpp.component.ComponentException;
import org.xmpp.packet.IQ;

@Component
public class DeskComponent extends AbstractComponent {

    private static final Logger log = LoggerFactory.getLogger(DeskComponent.class);
    public static final String NAMESPACE = "urn:xmpp:desk";

    @Autowired
    ActionsMapper actionsMapper;

    @Autowired
    DeskComponentService deskComponentService;

    @Override
    protected String[] discoInfoFeatureNamespaces() {
        return new String[]{NAMESPACE};
    }

    @Override
    public String getName() {
        return "TecFrac Support";
    }

    @Override
    public String getDescription() {
        return "Support Customers by Opening Tickets";
    }

    @Override
    public void start() {
        log.info("start component");
    }

    @Override
    protected IQ handleIQSet(IQ iq) throws Exception {
        System.out.print("IQ :" + iq);
        log.info("IQ :" + iq);
        RequestInfo requestInfo = IQUtils.getRequestInfo(iq);
        log.info("RequestInfo : " + requestInfo);
        TicketBean ticket = deskComponentService.getValueById(requestInfo.getTicketId());
        String action = requestInfo.getAction();
        if (!Objects.equals(ActionsMapper.CREATE_TICKET, action) && ticket == null) {
            return IQUtils.failureResponse(iq, "error");
        }

        UserBean user = deskComponentService.getValueByJID(requestInfo.getUserJID());
        if (user == null) {
            return IQUtils.failureResponse(iq, "not_found");
        }
        Boolean isBlocked = user.isBlocked();
        if (isBlocked) {
            log.info("user is blocked");
            return IQUtils.failureResponse(iq, "create_ticket.user_is_blocked");
        }

        Return<ModelUserAction> hasAction = deskComponentService.UserHasAction(user.getUserType().getId(), action);
        if (!hasAction.isSuccessfull()) {
            return IQUtils.failureResponse(iq, hasAction.getError());
        }

        Action actionHandler = actionsMapper.getActionHandler(action);

        if (actionHandler == null) {
            return IQUtils.failureResponse(iq, "action_not_supported");
        }
        Return<TicketBean> result = actionHandler.doAction(ticket, requestInfo);
        if (!result.isSuccessfull()) {
            return IQUtils.failureResponse(iq, result.getError());
        }
        IQ response = IQUtils.successResponse(iq, ticket.getId().toString(), actionHandler.toString() + " success");
        return response;
    }
}
