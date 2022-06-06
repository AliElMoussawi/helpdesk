package com.tecfrac.helpdesk.request;

import java.util.ArrayList;
import java.util.List;

public class RequestDeleteTickets {

  List<Integer> ticketsIds=new ArrayList<Integer>();
    public List<Integer> getTicketsIds() {
        return ticketsIds;
    }

    public void setTicketsIds( List<Integer> ticketsIds) {
        this.ticketsIds = ticketsIds;
    }

}
