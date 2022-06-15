package com.tecfrac.helpdesk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "user")
public class ModelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private ModelUserType userType;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "boolean default 'false'")
    private boolean blocked;

    @ManyToOne
    private ModelCompany company;
//

    public ModelUser() {
    }

    public ModelUser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModelUserType getUserType() {
        return userType;
    }

    public void setUserType(ModelUserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public ModelCompany getCompany() {
        return company;
    }

    public void setCompany(ModelCompany company) {
        this.company = company;
    }

}
