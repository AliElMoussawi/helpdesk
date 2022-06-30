package com.tecfrac.helpdesk.request;

import com.tecfrac.helpdesk.service.GroupService.PairUserInfo;
import java.util.List;

public class RequestGroupsUsers {

    private String name;
    private Long id;
    private List<PairUserInfo<String, Long, String>> user;

    public List<PairUserInfo<String, Long, String>> getUser() {
        return user;
    }

    public void setUser(List<PairUserInfo<String, Long, String>> user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
