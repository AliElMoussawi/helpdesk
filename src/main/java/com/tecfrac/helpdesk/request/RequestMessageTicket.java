package com.tecfrac.helpdesk.request;

import java.util.Date;
import java.util.List;

public class RequestMessageTicket {

    private String subject;

    private Long statusId;

    private Long requesterId;

    private Long ccId;

    private String message;
    private Long sendfrom;

    public Long getCcId() {
        return ccId;
    }

    public void setCcId(Long ccId) {
        this.ccId = ccId;
    }

    public Long getSendfrom() {
        return sendfrom;
    }

    public void setSendfrom(Long sendfrom) {
        this.sendfrom = sendfrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getccId() {
        return ccId;
    }

    public void setccId(Long ccId) {
        this.ccId = ccId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
