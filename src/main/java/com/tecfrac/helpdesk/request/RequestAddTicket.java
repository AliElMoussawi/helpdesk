package com.tecfrac.helpdesk.request;

import java.util.List;

public class RequestAddTicket {

    private String subject;

    private Integer statusId;

    private Integer requesterId;

    private Integer assignedUserId;

    private Integer assignedGroupId;

    private Integer priorityId;

    private Integer typeId;
    private List<String> tags;

    private String message;

    private Boolean valid;

    private Boolean internal;

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

    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Integer getAssignedGroupId() {
        return assignedGroupId;
    }

    public void setAssignedGroupId(Integer assignedGroupId) {
        this.assignedGroupId = assignedGroupId;
    }

    public Integer getPriortiyId() {
        return priorityId;
    }

    public void setPriortiyId(Integer priortiyId) {
        this.priorityId = priortiyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

}
