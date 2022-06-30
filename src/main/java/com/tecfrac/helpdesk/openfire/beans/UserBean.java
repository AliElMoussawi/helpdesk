package com.tecfrac.helpdesk.openfire.beans;

import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserType;

public class UserBean implements Bean {

    private String username;

    private Boolean blocked;

    private ModelUserType userType;

    public UserBean(String username, Boolean blocked, ModelUserType userType) {
        super();
        this.username = username;
        this.userType = userType;
        this.blocked = blocked;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isBlocked() {
        return blocked;
    }

    public void isBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public ModelUserType getUserType() {
        return userType;
    }

    public void setUserType(ModelUserType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserBean fromModel(ModelUser user) {
        return new UserBean(user.getUsername(), user.isBlocked(), user.getUserType());
    }
}
