package com.tecfrac.helpdesk.request;

import java.util.List;

public class RequestMergeTickets {

    Long primaryTicketId;
    List<Long> mergeTicketId;

    public Long getPrimaryTicketId() {
        return primaryTicketId;
    }

    public void setPrimaryTicketId(Long primaryTicketId) {
        this.primaryTicketId = primaryTicketId;
    }

    public List<Long> getMergeTicketId() {
        return mergeTicketId;
    }

    public void setMergeTicketId(List<Long> mergeTicketId) {
        this.mergeTicketId = mergeTicketId;
    }
}
