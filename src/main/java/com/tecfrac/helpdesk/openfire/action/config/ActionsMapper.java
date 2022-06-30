package com.tecfrac.helpdesk.openfire.action.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tecfrac.helpdesk.openfire.action.AddNotes;
import com.tecfrac.helpdesk.openfire.action.AssignAgent;
import com.tecfrac.helpdesk.openfire.action.BlockUser;
import com.tecfrac.helpdesk.openfire.action.CloseTicket;
import com.tecfrac.helpdesk.openfire.action.CreateTicket;
import com.tecfrac.helpdesk.openfire.action.DeleteAgent;
import com.tecfrac.helpdesk.openfire.action.DeleteGroup;
import com.tecfrac.helpdesk.openfire.action.GetHistory;
import com.tecfrac.helpdesk.openfire.action.GetTickets;
import com.tecfrac.helpdesk.openfire.action.KickMember;
import com.tecfrac.helpdesk.openfire.action.ReopenTicket;
import com.tecfrac.helpdesk.openfire.action.SendMessage;
import com.tecfrac.helpdesk.openfire.action.UpdateStatus;
import org.springframework.stereotype.Component;
@Component
public class ActionsMapper {

    public static final String CREATE_TICKET = "create-ticket";
    public static final String REOPEN_TICKET = "reopen-ticket";
    public static final String SEND_MESSAGE = "send-message";
    public static final String GET_HISTORY = "get-history";
    public static final String UPDATE_STATUS = "update-status";
    public static final String ADD_NOTES = "add-notes";
    public static final String ASSIGN_AGENT = "assign-agent";
    public static final String GET_TICKETS = "get-tickets";
    public static final String CLOSE_TICKET = "close-ticket";
    public static final String DELETE_GROUP = "delete-group";
    public static final String DELETE_AGENT = "delete-agent";
    public static final String KICK_MEMBER = "kick-member";
    public static final String BLOCK_USER = "block-user";

    private Map<String, Action> actions = new HashMap<String, Action>();

    public ActionsMapper() {
        Map<String, Action> initMap = new HashMap<String, Action>();

        initMap.put(CREATE_TICKET, new CreateTicket());
        initMap.put(REOPEN_TICKET, new ReopenTicket());
        initMap.put(SEND_MESSAGE, new SendMessage());
        initMap.put(GET_HISTORY, new GetHistory());
        initMap.put(UPDATE_STATUS, new UpdateStatus());
        initMap.put(ADD_NOTES, new AddNotes());
        initMap.put(ASSIGN_AGENT, new AssignAgent());
        initMap.put(GET_TICKETS, new GetTickets());
        initMap.put(CLOSE_TICKET, new CloseTicket());
        initMap.put(DELETE_GROUP, new DeleteGroup());
        initMap.put(DELETE_AGENT, new DeleteAgent());
        initMap.put(KICK_MEMBER, new KickMember());
        initMap.put(BLOCK_USER, new BlockUser());

        actions = Collections.unmodifiableMap(initMap);
    }

    public Action getActionHandler(String action) {
        return actions.get(action);
    }

}
