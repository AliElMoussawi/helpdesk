package com.tecfrac.helpdesk.request;

import java.util.List;

public class RequestGetTicket {

    private boolean mygroup = false;
    private boolean recently = false;
    private boolean unAssignedUser = false;
    private boolean onlyMine;
    private List<Long> statusIds;
    private Long requesterId;

    public boolean isMygroup() {
        return mygroup;
    }

    public void setMygroup(boolean mygroup) {
        this.mygroup = mygroup;
    }

    public boolean isRecently() {
        return recently;
    }

    public void setRecently(boolean recently) {
        this.recently = recently;
    }

    public boolean isUnAssignedUser() {
        return unAssignedUser;
    }

    public void setUnAssignedUser(boolean unAssignedUser) {
        this.unAssignedUser = unAssignedUser;
    }

    public boolean isOnlyMine() {
        return onlyMine;
    }

    public void setOnlyMine(boolean onlyMine) {
        this.onlyMine = onlyMine;
    }

    public List<Long> getStatusIds() {
        return statusIds;
    }

    public void setStatusIds(List<Long> statusIds) {
        this.statusIds = statusIds;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    private Long priorityId;
    private Long typeId;

}
