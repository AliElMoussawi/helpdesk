package com.tecfrac.helpdesk.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_message")
public class ModelTicketMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn
    private ModelUser user;

    @ManyToOne
    @JoinColumn
    private ModelTicket ticket;

    @Column
    private Date dateCreation;
    @ManyToOne
    @JoinColumn
    private ModelUserType useType;
    private Boolean inTernal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public ModelTicket getTicket() {
        return ticket;
    }

    public void setTicket(ModelTicket ticket) {
        this.ticket = ticket;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ModelUserType getUseType() {
        return useType;
    }

    public void setUseType(ModelUserType useType) {
        this.useType = useType;
    }

    public Boolean getInTernal() {
        return inTernal;
    }

    public void setInTernal(Boolean inTernal) {
        this.inTernal = inTernal;
    }

}
