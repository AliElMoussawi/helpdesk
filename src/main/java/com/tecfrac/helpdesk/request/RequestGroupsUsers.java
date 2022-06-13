package com.tecfrac.helpdesk.request;

import com.tecfrac.helpdesk.service.GroupService.PairUG;
import java.util.List;

public class RequestGroupsUsers {

    private String name;
    private Integer id;
    private List<PairUG<String, Integer>> user;

    public List<PairUG<String, Integer>> getUser() {
        return user;
    }

    public void setUser(List<PairUG<String, Integer>> user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
