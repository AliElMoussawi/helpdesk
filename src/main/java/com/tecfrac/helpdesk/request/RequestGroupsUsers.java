package com.tecfrac.helpdesk.request;

import com.tecfrac.helpdesk.service.GroupService.PairUserInfo;
import java.util.List;

public class RequestGroupsUsers {

    private String name;
    private Integer id;
    private List<PairUserInfo<String, Integer, String>> user;

    public List<PairUserInfo<String, Integer, String>> getUser() {
        return user;
    }

    public void setUser(List<PairUserInfo<String, Integer, String>> user) {
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
