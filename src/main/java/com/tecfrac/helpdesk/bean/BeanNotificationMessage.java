package com.tecfrac.helpdesk.bean;

import java.util.List;

public class  BeanNotificationMessage<T> {

    private List<T> data;
    private String action;

    public BeanNotificationMessage() {
    }

    public BeanNotificationMessage(List<T> data, String action) {
        this.data = data;
        this.action = action;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
