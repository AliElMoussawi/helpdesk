package com.tecfrac.helpdesk.request;

import java.util.List;

public class RequestAddTicket {

    private Long id;
    private String subject;
    private Long statusId;
    private Long requesterId;
    private Long assignedUserId;
    private Long assignedGroupId;
    private Long priorityId;
    private Long typeId;
    private List<String> tags;
    private String message;
    private Boolean valid;
    private Boolean internal;
    private Boolean delete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
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

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Long getAssignedGroupId() {
        return assignedGroupId;
    }

    public void setAssignedGroupId(Long assignedGroupId) {
        this.assignedGroupId = assignedGroupId;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priortiyId) {
        this.priorityId = priortiyId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
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
