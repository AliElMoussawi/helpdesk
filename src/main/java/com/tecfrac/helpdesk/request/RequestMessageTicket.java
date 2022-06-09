package com.tecfrac.helpdesk.request;

import java.util.Date;
import java.util.List;

public class RequestMessageTicket {

    private String subject;

    private Integer statusId;

    private Integer requesterId;

    private Integer ccId;
  
    private String message;
    private Integer sendfrom;

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getSendfrom() {
        return sendfrom;
    }

    public void setSendfrom(Integer sendfrom) {
        this.sendfrom = sendfrom;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public Integer getccId() {
        return ccId;
    }

    public void setccId(Integer ccId) {
        this.ccId = ccId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
