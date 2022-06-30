package com.tecfrac.helpdesk.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_action")
public class ModelUserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_type_id")
    private Integer userTypeId;

    @Column(name = "action_id")
    private Long actionId;

    @Column(name = "date_active")
    private Date dateActive = new Date();

    @Column(name = "date_deactive")
    private Date dateDeactive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Date getDateActive() {
        return dateActive;
    }

    public void setDateActive(Date dateActive) {
        this.dateActive = dateActive;
    }

    public Date getDateDeactive() {
        return dateDeactive;
    }

    public void setDateDeactive(Date dateDeactive) {
        this.dateDeactive = dateDeactive;
    }

}
