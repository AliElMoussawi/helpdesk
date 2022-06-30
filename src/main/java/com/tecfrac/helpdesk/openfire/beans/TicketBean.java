package com.tecfrac.helpdesk.openfire.beans;

import com.tecfrac.helpdesk.model.ModelTicket;
import java.util.Date;

public class TicketBean implements Bean {

    private Long id;

    private String assignedGroup;

    private Long status;

    private String assignedUser;

    private Date requested;

    private Date updated;

    public TicketBean(Long id, String assignedGroup, Long status,
            String assignedUser, Date requested, Date updated) {
        super();
        this.id = id;
        this.assignedGroup = assignedGroup;
        this.status = status;
        this.assignedUser = assignedUser;
        this.requested = requested;
        this.updated = updated;
    }

    public TicketBean() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupJid() {
        return assignedGroup;
    }

    public void setGroupJid(String assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getAssigneeUsername() {
        return assignedUser;
    }

    public void setAssigneeUsername(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Date getDateCreate() {
        return requested;
    }

    public void setDateCreate(Date requested) {
        this.requested = requested;
    }

    public Date getDateModified() {
        return updated;
    }

    public void setDateModified(Date updated) {
        this.updated = updated;
    }

    public static TicketBean fromModel(ModelTicket ticket) {
        Date dateCreation = ticket.getRequested();
        Date updated = ticket.getUpdated();

        return new TicketBean(ticket.getId(), ticket.getAssignedGroup().getJid(), ticket.getStatus().getId(), ticket.getAssignedUser().getUsername(),
                dateCreation, updated);
    }
}
