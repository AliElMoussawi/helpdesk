package com.tecfrac.helpdesk.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author CLICK ONCE
 */
@Entity
@Table(name = "`group`")
public class ModelGroup {

    public static final int Default = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    @Column
    private String jid;
    @ManyToOne
    private ModelCompany company;

    @OneToMany(mappedBy = "group")
    List<ModelUserGroup> user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelCompany getCompany() {
        return company;
    }

    public void setCompany(ModelCompany company) {
        this.company = company;
    }

    public List<ModelUserGroup> getUser() {
        return user;
    }

    public void setUser(List<ModelUserGroup> user) {
        this.user = user;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

}
