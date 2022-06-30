/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author CLICK ONCE
 */
@Entity
@Table(name = "Ticket")
public class ModelTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String subject;

    @JoinColumn
    @OneToOne
    private ModelTicketStatus status;

    @ManyToOne
    @JoinColumn
    private ModelUser requester;
    @ManyToOne
    @JoinColumn
    private ModelTicketType TicketType;
    @ManyToOne
    @JoinColumn
    private ModelTicketPriority priority;

    @ManyToOne
    @JoinColumn
    private ModelUser assignedUser;

//    @Column
//    private Set<ModelTag> modelTags;
    @ManyToOne
    @JoinColumn
    private ModelGroup assignedGroup;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date requested;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date updated;

    @Column
    public boolean valid;

    @JoinColumn
    @ManyToOne
    public ModelUser deletedBy;
    @Column
    private String note;
//    @ManyToOne
//    @JoinColumn
//    @JsonIgnore
//    private ModelTicket ticket;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ModelUser getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(ModelUser deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ModelTicketStatus getStatus() {
        return status;
    }

    public void setStatus(ModelTicketStatus status) {
        this.status = status;
    }

    public ModelUser getRequester() {
        return requester;
    }

    public void setRequester(ModelUser requester) {
        this.requester = requester;
    }

    public ModelTicketType getTicketType() {
        return TicketType;
    }

    public void setTicketType(ModelTicketType TicketType) {
        this.TicketType = TicketType;
    }

    public ModelTicketPriority getPriority() {
        return priority;
    }

    public void setPriority(ModelTicketPriority priority) {
        this.priority = priority;
    }

    public ModelUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(ModelUser assignedUser) {
        this.assignedUser = assignedUser;
    }

    public ModelGroup getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(ModelGroup assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public Date getRequested() {
        return requested;
    }

    public void setRequested(Date requested) {
        this.requested = requested;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
