package com.tecfrac.helpdesk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author CLICK ONCE
 */
@Entity
@Table(name = "session")
public class ModelSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ModelUser user;

    @Column(name = "token")
    private String token;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_creation")
    private Date dateCreation = new Date();
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_expired")
    private Date dateExpired;

    public Integer getId() {
        return id;
    }
    @Column(name = "valid")
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public Boolean setValid(boolean valid) {
        this.valid = valid;
        return valid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String Token) {
        this.token = Token;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

}
