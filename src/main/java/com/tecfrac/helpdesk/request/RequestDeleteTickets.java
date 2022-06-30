package com.tecfrac.helpdesk.request;

import java.util.ArrayList;
import java.util.List;

public class RequestDeleteTickets {

    List<Long> ticketsIds = new ArrayList<>();

    public List<Long> getTicketsIds() {
        return ticketsIds;
    }

    public void setTicketsIds(List<Long> ticketsIds) {
        this.ticketsIds = ticketsIds;
    }

}
