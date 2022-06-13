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

    public static final int Default = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;
    @ManyToOne
    @JoinColumn
    private ModelCompany company;

    @ManyToMany(mappedBy = "group")
    List<ModelUserGroup> user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}
