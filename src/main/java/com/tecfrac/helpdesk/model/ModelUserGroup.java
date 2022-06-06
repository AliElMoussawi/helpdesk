package com.tecfrac.helpdesk.model;

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
@Table(name = "user_group")
public class ModelUserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(unique = true)
    private ModelGroup group;
    @ManyToOne
    @JoinColumn(unique = true)
    private ModelUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModelGroup getGroup() {
        return group;
    }

    public void setGroup(ModelGroup group) {
        this.group = group;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

}
